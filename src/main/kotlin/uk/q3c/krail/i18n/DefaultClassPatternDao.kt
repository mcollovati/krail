/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */

package uk.q3c.krail.i18n

import com.google.common.base.Preconditions.checkNotNull
import com.google.inject.Inject
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.apache.commons.lang3.ClassUtils
import org.slf4j.LoggerFactory
import uk.q3c.krail.i18n.api.*
import uk.q3c.krail.i18n.api.clazz.ClassPatternDao
import uk.q3c.krail.i18n.clazz.ClassBundleControl
import uk.q3c.krail.i18n.clazz.ClassPatternSource
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset
import java.nio.charset.CodingErrorAction
import java.util.*

/**
 * A [PatternDao] implementation used with [EnumResourceBundle] instances held within code.  Writing back to source code is clearly not an option,
 * but this implementation can be set up to write source code files to a defined external directory.
 *
 *
 * Created by David Sowerby on 27/07/15.
 */
class DefaultClassPatternDao @Inject constructor(val control: ClassBundleControl, val config: ClassPatternDaoConfig) :

        ClassPatternDaoConfig by config, ClassPatternDao {


    private val log = LoggerFactory.getLogger(this.javaClass.name)

    override var writeFile: File? = null

    init {
        config.dao = this
    }


    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EXS_EXCEPTION_SOFTENING_NO_CHECKED")
    override fun write(cacheKey: PatternCacheKey, value: String): Any {
        checkNotNull(cacheKey)
        checkNotNull(value)
        if (writeFile == null) {
            throw PatternWriteException("Write file must be set")
        }
        if (!writeFile!!.exists()) {
            throw PatternWriteException("Write file must exist")
        }
        val indent = "    "
        val indent2 = indent + indent
        val buf = StringBuilder(indent2)
        buf.append("put(")
                .append(cacheKey.keyAsEnum
                        .name)
                .append(", \"")
                .append(value)
                .append("\");\n")


        val encoder = Charset.forName("UTF-8")
                .newEncoder()
        encoder.onMalformedInput(CodingErrorAction.REPORT)
        encoder.onUnmappableCharacter(CodingErrorAction.REPORT)

        val output = buf.toString()
        try {
            BufferedWriter(OutputStreamWriter(FileOutputStream(writeFile!!), encoder)).use { writer ->
                writer.write(output)
                return output
            }
        } catch (e: Exception) {
            throw PatternWriteException("failed to write pattern", e)
        }

    }

    /**
     * {@inheritDoc}
     */
    override fun deleteValue(cacheKey: PatternCacheKey): Optional<String> {
        throw UnsupportedOperationException("Class based I18NPatterns cannot be deleted")
    }

    /**
     * {@inheritDoc}
     */
    override fun getValue(cacheKey: PatternCacheKey): Optional<String> {
        checkNotNull(cacheKey)
        // source is used to qualify the Option
        log.debug("getValue for cacheKey {}, source '{}', using control: {}", cacheKey, ClassPatternSource::class.java, getControl().javaClass
                .simpleName)
        val key = cacheKey.key
        val expandedBaseName = expandFromKey(key)
        try {
            val bundle = ResourceBundle.getBundle(expandedBaseName, cacheKey.actualLocale, getControl())
            val value: String? = getValue(bundle, cacheKey.keyAsEnum)
            if (value == null) {
                return Optional.empty()
            } else {
                return Optional.of(value)
            }
        } catch (e: Exception) {
            log.warn("returning empty value, as getValue() returned exception {} with message '{}'", e, e.message)
            return Optional.empty<String>()
        }

    }

    protected fun getValue(bundle: ResourceBundle, key: Enum<*>): String? {
        return ClassPatternDaoHelper().getValue(bundle, key)
    }

    /**
     * Allows the setting of paths for location of class and property files.  The bundle base name is taken from [I18NKey.bundleName].
     *
     *
     * [config] entries determine how the bundle name is expanded.  If [ClassPatternDaoConfig.useKeyPath] is true, the bundle name is
     * appended to the package path of the `sampleKey`
     *
     *
     * If [ClassPatternDaoConfig.useKeyPath] is false, the bundle name is appended to [ClassPatternDaoConfig.pathToValues]

     * @param sampleKey any key from the I18NKey class, to give access to bundleName()
     * *
     * @return a path constructed from the `sampleKey` and [config.pathToValues] values
     */
    protected fun expandFromKey(sampleKey: I18NKey): String {
        checkNotNull(sampleKey)
        val baseName = sampleKey.bundleName()
        val packageName: String
        //use source to qualify the options, so they get their own, and not the base class
        if (config.useKeyPath) {
            packageName = ClassUtils.getPackageCanonicalName(sampleKey.javaClass)

        } else {
            val pathOptionValue = config.pathToValues
            if (pathOptionValue.isEmpty() || "." == pathOptionValue) {
                packageName = ClassUtils.getPackageCanonicalName(sampleKey.javaClass)
            } else {
                packageName = pathOptionValue
            }
        }

        return if (packageName.isEmpty()) baseName else packageName + '.' + baseName
    }


    fun getControl(): ResourceBundle.Control {
        return control
    }

    /**
     * Returns [DefaultClassPatternDao.CONNECTION_URL] as a connection url

     * @return [DefaultClassPatternDao.CONNECTION_URL] as a connection url
     */
    override fun connectionUrl(): String {
        return DefaultClassPatternDao.CONNECTION_URL
    }

    /**
     * {@inheritDoc}
     */
    override fun count(): Long {
        throw UnsupportedOperationException("count is not available for class based patterns")
    }


    companion object {
        val CONNECTION_URL = "Class based"

    }
}
