package uk.q3c.krail.core.i18n

import com.google.inject.Inject
import uk.q3c.krail.i18n.api.PatternCacheLoader
import uk.q3c.krail.i18n.api.PatternCacheLoaderConfig
import uk.q3c.krail.option.api.Option
import uk.q3c.krail.option.api.OptionContext
import uk.q3c.krail.option.api.OptionKey

/**
 * Created by David Sowerby on 04 Aug 2017
 */
class PatternCacheLoaderConfigByOption @Inject constructor(val option: Option) : PatternCacheLoaderConfig, OptionContext<Any> {


    val optionKeyAutoStub: OptionKey<Boolean> = OptionKey(false, PatternCacheLoaderConfigByOption::class.java, LabelKey.Auto_Stub,
            DescriptionKey.Auto_Stub)
    val optionKeyStubWithKeyName: OptionKey<Boolean> = OptionKey(true, PatternCacheLoaderConfigByOption::class.java, LabelKey
            .Stub_with_Key_Name, DescriptionKey.Stub_with_Key_Name)
    val optionKeyStubValue: OptionKey<String> = OptionKey("undefined", PatternCacheLoaderConfigByOption::class.java, LabelKey.Stub_Value,
            DescriptionKey.Stub_Value)

    override lateinit var patternCacheLoader: PatternCacheLoader

    override fun autoStub(source: Class<out Annotation>): Boolean {
        return option[optionKeyAutoStub.qualifiedWith(source.simpleName)]
    }

    override fun stubWithKeyName(source: Class<out Annotation>): Boolean {
        return option[optionKeyStubWithKeyName.qualifiedWith(source.simpleName)]
    }

    override fun stubValue(source: Class<out Annotation>): String {
        return option[optionKeyStubValue.qualifiedWith(source.simpleName)]
    }

    override fun setAutoStub(source: Class<out Annotation>, value: Boolean) {
        option.set(optionKeyAutoStub.qualifiedWith(source.simpleName), value)
    }

    override fun setStubWithKeyName(source: Class<out Annotation>, value: Boolean) {
        option.set(optionKeyStubWithKeyName.qualifiedWith(source.simpleName), value)
    }

    override fun setStubValue(source: Class<out Annotation>, value: String) {
        option.set(optionKeyStubValue.qualifiedWith(source.simpleName), value)
    }


    override fun optionInstance(): Option {
        return option
    }

    override fun optionValueChanged(event: Any?) {
        // nothing to do
    }
}