package uk.q3c.krail.i18n

import com.google.inject.Inject
import uk.q3c.krail.core.i18n.DescriptionKey
import uk.q3c.krail.core.i18n.LabelKey
import uk.q3c.krail.core.option.AnnotationOptionList
import uk.q3c.krail.core.option.Option
import uk.q3c.krail.core.option.OptionContext
import uk.q3c.krail.core.option.OptionKey
import uk.q3c.krail.i18n.api.PatternSourceProviderConfig

/**
 * Created by David Sowerby on 04 Aug 2017
 */
class PatternSourceProviderConfigByOption @Inject constructor(val option: Option) : PatternSourceProviderConfig, OptionContext<Any> {

    val optionKeySourceOrder: OptionKey<AnnotationOptionList> =
            OptionKey(AnnotationOptionList(), PatternSourceProviderConfigByOption::class.java, LabelKey.Source_Order, DescriptionKey.Source_Order)

    val optionKeySourceOrderDefault: OptionKey<AnnotationOptionList> = OptionKey(AnnotationOptionList(), PatternSourceProviderConfigByOption::class.java,
            LabelKey.Source_Order_Default, DescriptionKey.Source_Order_Default)

    val optionKeySelectedTargets: OptionKey<AnnotationOptionList> = OptionKey(AnnotationOptionList(), PatternSourceProviderConfigByOption::class.java,
            LabelKey.Selected_Pattern_Targets, DescriptionKey.Selected_Pattern_Targets)

    override lateinit var patternSourceProvider: PatternSourceProvider

    override var sourceOrder: AnnotationOptionList = AnnotationOptionList()
        get() {
            return option[optionKeySourceOrder]
        }
        set(value) {
            field = value
            option.set(optionKeySourceOrder, value)
        }


    override var sourceOrderDefault: AnnotationOptionList = AnnotationOptionList()
        get() {
            return option[optionKeySourceOrderDefault]
        }
        set(value) {
            field = value
            option.set(optionKeySourceOrderDefault, value)
        }

    override var selectedTargets: AnnotationOptionList = AnnotationOptionList()
        get() {
            return option[optionKeySelectedTargets]
        }
        set(value) {
            field = value
            option.set(optionKeySelectedTargets, value)
        }

    override fun optionInstance(): Option {
        return option
    }

    override fun optionValueChanged(event: Any?) {
        // nothing to do
    }


}