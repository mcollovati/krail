package uk.q3c.krail.core.i18n

import uk.q3c.krail.i18n.I18NConfigModule
import uk.q3c.krail.i18n.api.clazz.ClassPatternDaoConfig

/**
 * Created by David Sowerby on 03 Aug 2017
 */
class KrailI18NConfigModule : I18NConfigModule() {

    override fun bindClassPatternDaoConfig() {
        bind(ClassPatternDaoConfig::class.java).to(ClassPatternDaoConfigByOption::class.java)
    }
}
