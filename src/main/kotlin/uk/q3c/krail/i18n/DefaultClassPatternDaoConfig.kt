package uk.q3c.krail.i18n

import uk.q3c.krail.i18n.api.ClassPatternDaoConfig
import uk.q3c.krail.i18n.api.clazz.ClassPatternDao

/**
 * Created by David Sowerby on 03 Aug 2017
 */
data class DefaultClassPatternDaoConfig(override var pathToValues: String, override var useKeyPath: Boolean) : ClassPatternDaoConfig {
    override lateinit var dao: ClassPatternDao
}