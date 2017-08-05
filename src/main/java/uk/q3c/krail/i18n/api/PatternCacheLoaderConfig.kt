package uk.q3c.krail.i18n.api

/**
 * Created by David Sowerby on 04 Aug 2017
 */
interface PatternCacheLoaderConfig {
    var patternCacheLoader: PatternCacheLoader

    fun autoStub(source: Class<out Annotation>): Boolean
    fun stubWithKeyName(source: Class<out Annotation>): Boolean
    fun stubValue(source: Class<out Annotation>): String

    fun setAutoStub(source: Class<out Annotation>, value: Boolean)
    fun setStubWithKeyName(source: Class<out Annotation>, value: Boolean)
    fun setStubValue(source: Class<out Annotation>, value: String)


}