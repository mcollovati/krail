package uk.q3c.krail.i18n.api

import uk.q3c.krail.core.option.AnnotationOptionList

/**
 * Created by David Sowerby on 04 Aug 2017
 */
interface PatternSourceProviderConfig {

    var sourceOrder: AnnotationOptionList
    var sourceOrderDefault: AnnotationOptionList
    var selectedTargets: AnnotationOptionList
    var patternSourceProvider: PatternSourceProvider

}