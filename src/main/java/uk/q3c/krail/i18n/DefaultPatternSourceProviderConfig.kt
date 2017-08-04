package uk.q3c.krail.i18n

import com.google.inject.Inject
import uk.q3c.krail.core.option.AnnotationOptionList
import uk.q3c.krail.i18n.api.PatternSourceProviderConfig

/**
 * Created by David Sowerby on 04 Aug 2017
 */
class DefaultPatternSourceProviderConfig @Inject constructor(
        override var sourceOrder: AnnotationOptionList,
        override var sourceOrderDefault: AnnotationOptionList,
        override var selectedTargets: AnnotationOptionList)

    : PatternSourceProviderConfig {

    override lateinit var patternSourceProvider: PatternSourceProvider


}