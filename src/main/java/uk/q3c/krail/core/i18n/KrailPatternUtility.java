package uk.q3c.krail.core.i18n;

import uk.q3c.krail.i18n.KrailI8NModule;
import uk.q3c.krail.i18n.api.PatternDao;
import uk.q3c.krail.i18n.api.PatternUtility;

/**
 * Extends the {@link PatternUtility} to enable export of all the I18NKeys (and values) from the Krail core to {@code target}
 * <p>
 * Created by David Sowerby on 06 Aug 2017
 */
public interface KrailPatternUtility extends PatternUtility {

    /**
     * Exports all the core Krail I18NKeys for all supported Locales (as defined by the {@link KrailI8NModule}
     *
     * @param target the PatternDao to export to
     * @return a count of all the keys exported
     */
    long exportCoreKeys(PatternDao target);
}
