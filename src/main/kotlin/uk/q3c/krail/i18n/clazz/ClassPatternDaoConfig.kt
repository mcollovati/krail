package uk.q3c.krail.i18n.api

import uk.q3c.krail.i18n.api.clazz.ClassPatternDao

/**
 * Created by David Sowerby on 03 Aug 2017
 */
interface ClassPatternDaoConfig {
    var dao: ClassPatternDao
    var pathToValues: String
    var useKeyPath: Boolean
}



