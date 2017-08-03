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

import org.apache.commons.io.FileUtils
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import uk.q3c.krail.core.i18n.LabelKey
import uk.q3c.krail.core.i18n.MessageKey
import uk.q3c.krail.core.option.Option
import uk.q3c.krail.i18n.api.PatternCacheKey
import uk.q3c.krail.i18n.api.PatternWriteException
import uk.q3c.krail.i18n.api.clazz.ClassPatternDaoConfig
import uk.q3c.krail.i18n.clazz.ClassBundleControl
import uk.q3c.krail.i18n.clazz.DefaultClassPatternDao
import uk.q3c.krail.i18n.clazz.DefaultClassPatternDaoConfig

/**
 * Unit test for {@link uk.q3c.krail.i18n.clazz.DefaultClassPatternDao}
 *
 * Created by David Sowerby on 27/07/15.
 */

class DefaultClassPatternDaoTest extends Specification {


    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder()

    DefaultClassPatternDao dao

    @SuppressWarnings("GroovyAssignabilityCheck")
    Option option = Mock()
    @SuppressWarnings("GroovyAssignabilityCheck")
    PatternCacheKey patternCacheKey = Mock()
    ClassPatternDaoConfig config = new DefaultClassPatternDaoConfig("", true)


    def setup() {
        dao = new DefaultClassPatternDao(new ClassBundleControl(), config)
    }


    def "count is not supported"() {
        when:
        dao.count()

        then:
        thrown UnsupportedOperationException
    }

    def "delete is not supported"() {
        when:
        dao.deleteValue(patternCacheKey)

        then:
        thrown UnsupportedOperationException
    }

    def "write with no path set should throw PatternWriteException"() {
        when:
        dao.write(patternCacheKey, "ww")

        then:
        thrown PatternWriteException
    }

    def "write path with invalid path should throw PatternWriteException"() {
        given:
        dao.setWriteFile(new File("rubbish"))

        when:
        dao.write(patternCacheKey, "ww")

        then:
        thrown PatternWriteException
    }

    def "write path to non-existent file should throw PatternWriteException"() {
        given:
        File targetFile = new File(temporaryFolder.getRoot(), "classPatternDao.txt")
        dao.setWriteFile(targetFile)

        when:
        dao.write(patternCacheKey, "ww")

        then:
        thrown PatternWriteException
    }

    def "write appends a correctly formatted line to specified file"() {
        given:
        patternCacheKey.key >> LabelKey.Yes
        patternCacheKey.getKeyAsEnum() >> (Enum) patternCacheKey.key
        File targetFile = new File(temporaryFolder.getRoot(), "classPatternDao.txt")
        dao.setWriteFile(targetFile)
        FileUtils.write(targetFile, "this would be the class info\n\n")

        when:
        dao.write(patternCacheKey, "ww")
        String output = FileUtils.readFileToString(targetFile)

        then:
        output.contains("        put(Yes, \"ww\");")

    }

    def "correct connection url"() {
        expect:
        dao.connectionUrl().equals(DefaultClassPatternDao.CONNECTION_URL)
    }


    def "expandFromKey() returns key path when useKeyPath is true"() {
        given:
        config.useKeyPath = true

        expect:
        dao.expandFromKey(LabelKey.Yes).equals("uk.q3c.krail.core.i18n.Labels")
    }


    def "expandFromKey() returns path same as the key when useKeyPath is false and pathToValues is not set"() {
        given:
        config.useKeyPath = false
        config.pathToValues = ""

        expect:
        dao.expandFromKey(LabelKey.Yes).equals("uk.q3c.krail.core.i18n.Labels")
    }

    def "expandFromKey() returns path same as the key when useKeyPath is false and pathToValues is set to '.'"() {
        given:
        config.useKeyPath = false
        config.pathToValues = "."


        expect:
        dao.expandFromKey(LabelKey.Yes).equals("uk.q3c.krail.core.i18n.Labels")
    }

    def "expandFromKey() returns optionPathToValues when optionKeyUseKeyPath is false and not empty or '.'"() {
        given:
        config.useKeyPath = false
        config.pathToValues = "com.example.i18n"


        expect:
        dao.expandFromKey(LabelKey.Yes).equals("com.example.i18n.Labels")
    }


    def "getValue() with no populated value should return Optional.empty()"() {
        given:
        patternCacheKey.key >> LabelKey.Yes
        patternCacheKey.getKeyAsEnum() >> (Enum) patternCacheKey.key
        config.useKeyPath = true

        expect:
        dao.getValue(patternCacheKey).equals(Optional.empty())
    }


    def "getValue() with populated value should return Optional.of(value)"() {
        given:
        patternCacheKey.key >> MessageKey.Invalid_URI
        patternCacheKey.getKeyAsEnum() >> (Enum) patternCacheKey.key
        patternCacheKey.getActualLocale() >> Locale.forLanguageTag("")
        config.useKeyPath = true

        expect:
        dao.getValue(patternCacheKey).equals(Optional.of("{0} is not a valid page"))
    }

    def "getValue() from alternative Locale should return correct pattern for the Locale"() {
        given:
        patternCacheKey.key >> MessageKey.Invalid_URI
        patternCacheKey.getKeyAsEnum() >> (Enum) patternCacheKey.key
        patternCacheKey.getActualLocale() >> Locale.forLanguageTag("de")
        config.useKeyPath = true

        expect:
        dao.getValue(patternCacheKey).equals(Optional.of("{0} ist keine gültige Seite"))
    }


    def "set and get write file"() {
        given:
        File file = Mock()

        when:

        dao.setWriteFile(file)

        then:

        dao.getWriteFile() == file
    }

    def "writeFile error"() {
        given:
        File file = new File(temporaryFolder.getRoot(), "rubbish")
        file.createNewFile()
        file.setReadOnly()

        patternCacheKey.key >> MessageKey.Invalid_URI
        patternCacheKey.getKeyAsEnum() >> (Enum) patternCacheKey.key
        patternCacheKey.getActualLocale() >> Locale.forLanguageTag("de")
        dao.setWriteFile(file)
        when:
        dao.write(patternCacheKey, "x")

        then:

        thrown PatternWriteException
    }

}
