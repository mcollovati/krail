package uk.q3c.krail.core.i18n;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import uk.q3c.krail.i18n.LabelKey;
import uk.q3c.krail.i18n.api.I18NKey;
import uk.q3c.krail.i18n.api.PatternDao;
import uk.q3c.krail.i18n.api.PatternUtility;
import uk.q3c.krail.i18n.api.SupportedLocales;
import uk.q3c.krail.i18n.validation.ValidationKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Set;

import static com.google.common.base.Preconditions.*;

/**
 * Created by David Sowerby on 06 Aug 2017
 */
public class DefaultKrailPatternUtility implements KrailPatternUtility {
    private final PatternUtility patternUtility;
    private final Set<Locale> supportedLocales;

    @Inject
    protected DefaultKrailPatternUtility(PatternUtility patternUtility, @SupportedLocales Set<Locale> supportedLocales) {

        this.patternUtility = patternUtility;
        this.supportedLocales = supportedLocales;
    }


    public long exportCoreKeys(PatternDao target) {
        checkNotNull(target);
        ImmutableSet<Class<? extends I18NKey>> bundles = ImmutableSet.of(LabelKey.class, DescriptionKey.class, MessageKey.class, ValidationKey.class);
        return patternUtility.export(target, bundles, supportedLocales);
    }

    @Override
    public long export(@Nonnull PatternDao source, @Nonnull PatternDao target, @Nonnull Set<Class<? extends I18NKey>> bundles, @Nonnull Set<Locale> locales, boolean autoStub, boolean stubWithKeyName, @Nullable String stubValue) {
        return patternUtility.export(source, target, bundles, locales, autoStub, stubWithKeyName, stubValue);
    }

    @Override
    public long export(@Nonnull PatternDao target, @Nonnull Set<Class<? extends I18NKey>> bundles, @Nonnull Set<Locale> locales) {
        return patternUtility.export(target, bundles, locales);
    }
}
