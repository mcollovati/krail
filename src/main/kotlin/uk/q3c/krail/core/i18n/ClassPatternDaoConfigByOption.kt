package uk.q3c.krail.core.i18n

import com.google.inject.Inject
import com.vaadin.data.Property
import uk.q3c.krail.core.option.Option
import uk.q3c.krail.core.option.OptionContext
import uk.q3c.krail.core.option.OptionKey
import uk.q3c.krail.i18n.api.ClassPatternDaoConfig
import uk.q3c.krail.i18n.api.clazz.ClassPatternDao
import uk.q3c.krail.i18n.clazz.ClassPatternSource

/**
 * Created by David Sowerby on 03 Aug 2017
 */
class ClassPatternDaoConfigByOption @Inject constructor(val option: Option) : ClassPatternDaoConfig, OptionContext {

    val sourceString: String = ClassPatternSource::class.java.simpleName
    override lateinit var dao: ClassPatternDao
    val optionPathToValues: OptionKey<String> =
            OptionKey("", ClassPatternDaoConfigByOption::class.java, LabelKey.Path, DescriptionKey.Path).qualifiedWith(sourceString)
    val optionKeyUseKeyPath: OptionKey<Boolean> =
            OptionKey(true, ClassPatternDaoConfigByOption::class.java, LabelKey.Use_Key_Path, DescriptionKey.Use_Key_Path).qualifiedWith(sourceString)


    override var pathToValues: String = ""
        get() {
            return option[optionPathToValues]
        }
        set(value) {
            field = value
            option.set(optionPathToValues, value)
        }

    override var useKeyPath: Boolean = true
        get() {
            return option[optionKeyUseKeyPath]
        }
        set(value) {
            field = value
            option.set(optionKeyUseKeyPath, value)
        }

    override fun optionValueChanged(event: Property.ValueChangeEvent) {
        //does nothing, option values are called as required
    }

    override fun optionInstance(): Option {
        return option
    }


}