package uk.q3c.krail.core.i18n

import spock.lang.Specification
import uk.q3c.krail.i18n.DefaultPatternUtility
import uk.q3c.krail.i18n.api.PatternSource
import uk.q3c.krail.i18n.api.PatternSourceProvider
import uk.q3c.krail.i18n.api.clazz.ClassPatternDao
import uk.q3c.krail.i18n.clazz.ClassPatternSource
import uk.q3c.krail.i18n.validation.ValidationKey

/**
 * Created by David Sowerby on 06 Aug 2017
 */
class DefaultKrailPatternUtilityTest extends Specification {
    DefaultKrailPatternUtility utility

    def patternUtility
    def patternSourceProvider = Mock(PatternSourceProvider)
    def patternSource = Mock(PatternSource)
    def targetPatternDao = Mock(ClassPatternDao)
    def sourcePatternDao = Mock(ClassPatternDao)
    Set<Locale> supportedLocales

    def setup() {
        patternUtility = new DefaultPatternUtility(patternSource, supportedLocales)
        supportedLocales = new HashSet<>()
        utility = new DefaultKrailPatternUtility(patternUtility, supportedLocales)
    }

    def "export core keys (which uses export with PatternSource) should be a count of all core keys"() {
        given:
        supportedLocales.addAll(Locale.UK, Locale.GERMANY)
        patternSourceProvider.orderedSources(_) >> ClassPatternSource
        patternSourceProvider.sourceFor(ClassPatternSource) >> sourcePatternDao
        long expectedCount = (ValidationKey.getEnumConstants().length + LabelKey.getEnumConstants().length + DescriptionKey.getEnumConstants().length + MessageKey.getEnumConstants().length) * supportedLocales.size()

        when:

        long count = utility.exportCoreKeys(targetPatternDao)

        then:

        count == expectedCount
        expectedCount * targetPatternDao.write(_, _)

    }
}
