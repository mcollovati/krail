package uk.q3c.krail.core.option;

import com.vaadin.data.Container;

import java.lang.annotation.Annotation;

/**
 * Created by David Sowerby on 10 Aug 2017
 */
public interface OptionContainerSource {

    /**
     * Returns a Vaadin container for the Option source identified by [annotationClass]
     *
     * @param annotationClass
     * @return
     */
    Container getContainer(Class<? extends Annotation> annotationClass);
}
