package uk.q3c.krail.i18n

import com.google.inject.Inject
import uk.q3c.krail.i18n.api.PatternSourceProvider
import uk.q3c.krail.i18n.api.PatternSourceProviderConfig
import uk.q3c.util.collection.AnnotationList

/**
 * Created by David Sowerby on 04 Aug 2017
 */
class DefaultPatternSourceProviderConfig @Inject constructor(
        override var sourceOrder: AnnotationList,
        override var sourceOrderDefault: AnnotationList,
        override var selectedTargets: AnnotationList)

    : PatternSourceProviderConfig {

    override lateinit var patternSourceProvider: PatternSourceProvider


}