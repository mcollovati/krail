package uk.q3c.krail.core.option;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.vaadin.data.Container;
import uk.q3c.krail.core.config.ConfigurationException;
import uk.q3c.krail.i18n.api.MessageFormat2;
import uk.q3c.krail.option.api.OptionDaoProviders;
import uk.q3c.krail.persist.api.PersistenceInfo;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.google.common.base.Preconditions.*;

/**
 * Created by David Sowerby on 10 Aug 2017
 */
public class DefaultOptionContainerSource implements OptionContainerSource {

    private Map<Class<? extends Annotation>, PersistenceInfo<?>> optionDaoProviders;
    private MessageFormat2 messageFormat;
    private Injector injector;

    @Inject
    protected DefaultOptionContainerSource(@OptionDaoProviders Map<Class<? extends Annotation>, PersistenceInfo<?>> optionDaoProviders, MessageFormat2 messageFormat, Injector injector) {
        this.optionDaoProviders = optionDaoProviders;
        this.messageFormat = messageFormat;
        this.injector = injector;
    }

    @Override
    public Container getContainer(Class<? extends Annotation> annotationClass) {
        checkAnnotationKey(annotationClass);
        Key<OptionContainerProvider> containerProviderKey = Key.get(OptionContainerProvider.class, annotationClass);
        OptionContainerProvider provider = injector.getInstance(containerProviderKey);
        return provider.get();
    }

    protected void checkAnnotationKey(Class<? extends Annotation> annotationClass) {
        checkNotNull(annotationClass);
        if (!optionDaoProviders.containsKey(annotationClass)) {
            String msg = messageFormat.format("The OptionDaoDelegate annotation of '{0}' does not match any of the providers.", annotationClass.getSimpleName
                    ());
            throw new ConfigurationException(msg);
        }
    }
}
