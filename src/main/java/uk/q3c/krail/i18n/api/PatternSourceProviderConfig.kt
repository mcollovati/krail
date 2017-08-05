package uk.q3c.krail.i18n.api

import uk.q3c.util.collection.AnnotationList

/**
 * Created by David Sowerby on 04 Aug 2017
 */
interface PatternSourceProviderConfig {

    var sourceOrder: AnnotationList
    var sourceOrderDefault: AnnotationList
    var selectedTargets: AnnotationList
    var patternSourceProvider: PatternSourceProvider

}