package uk.q3c.krail.i18n

import uk.q3c.krail.i18n.api.PatternCacheLoader
import uk.q3c.krail.i18n.api.PatternCacheLoaderConfig

/**
 * Created by David Sowerby on 04 Aug 2017
 */
class DefaultPatternCacheLoaderConfig : PatternCacheLoaderConfig {
    val values = mutableMapOf<Class<out Annotation>, PatternCacheLoaderConfigSet>()

    override fun setAutoStub(source: Class<out Annotation>, value: Boolean) {
        var set = values[source]
        if (set == null) {
            set = PatternCacheLoaderConfigSet(autoStub = value)
            values.put(source, set)
        } else {
            set.autoStub = value
        }

    }

    override fun setStubWithKeyName(source: Class<out Annotation>, value: Boolean) {
        var set = values[source]
        if (set == null) {
            set = PatternCacheLoaderConfigSet(stubWithKeyName = value)
            values.put(source, set)
        } else {
            set.stubWithKeyName = value
        }
    }

    override fun setStubValue(source: Class<out Annotation>, value: String) {
        var set = values[source]
        if (set == null) {
            set = PatternCacheLoaderConfigSet(stubValue = value)
            values.put(source, set)
        } else {
            set.stubValue = value
        }
    }

    override fun autoStub(source: Class<out Annotation>): Boolean {
        var set = values[source]
        if (set == null) {
            set = PatternCacheLoaderConfigSet()
            values.put(source, set)
        }
        return set.autoStub
    }

    override fun stubWithKeyName(source: Class<out Annotation>): Boolean {
        var set = values[source]
        if (set == null) {
            set = PatternCacheLoaderConfigSet()
            values.put(source, set)
        }
        return set.stubWithKeyName
    }

    override fun stubValue(source: Class<out Annotation>): String {
        var set = values[source]
        if (set == null) {
            set = PatternCacheLoaderConfigSet()
            values.put(source, set)
        }
        return set.stubValue
    }


    override lateinit var patternCacheLoader: PatternCacheLoader

}

data class PatternCacheLoaderConfigSet(var autoStub: Boolean = false, var stubWithKeyName: Boolean = true, var stubValue: String = "undefined")