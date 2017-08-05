/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */
package uk.q3c.krail.i18n;


import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import uk.q3c.krail.core.guice.uiscope.UIScoped;
import uk.q3c.krail.core.guice.vsscope.VaadinSessionScoped;
import uk.q3c.krail.core.i18n.DefaultI18NFieldScanner;
import uk.q3c.krail.core.i18n.LabelKey;
import uk.q3c.krail.core.option.Option;
import uk.q3c.krail.core.persist.common.common.KrailPersistenceUnitHelper;
import uk.q3c.krail.i18n.api.*;
import uk.q3c.krail.i18n.api.clazz.ClassPatternDao;
import uk.q3c.krail.i18n.clazz.ClassPatternSource;
import uk.q3c.krail.i18n.clazz.DefaultClassPatternDao;
import uk.q3c.util.clazz.DefaultUnenhancedClassIdentifier;
import uk.q3c.util.clazz.UnenhancedClassIdentifier;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * Configures I18N for an application.
 * <p>
 * An I18N source is the equivalent of a persistence unit (the class based, EnumResourceBundle provision of I18N is considered to be a single source / PU).
 * <p>
 * A source is represented by an annotation, for example {@link ClassPatternSource} - which is provided by this module.  Other persistence providers (for
 * example krail-jpa) will provide bindings to their own {@link #sources}, which Guice merges into a single map.
 * <p>
 * An I18NKey implementation - for example, {@link LabelKey}, and its associated {@link EnumResourceBundle}s, are the equivalent to a Java Resource bundle
 */

public class VaadinI18NModule extends I18NModule {

    private final TypeLiteral<Class<? extends Annotation>> annotationLiteral = KrailPersistenceUnitHelper.annotationClassLiteral();
    private final TypeLiteral<PatternDao> patternDaoTypeLiteral = new TypeLiteral<PatternDao>() {
    };

    private LinkedHashSet<Class<? extends Annotation>> prepSources = new LinkedHashSet<>(); // retain order;
    private Set<Class<? extends Annotation>> prepSourcesDefaultOrder = new LinkedHashSet<>();
    private Map<Class<? extends I18NKey>, LinkedHashSet<Class<? extends Annotation>>> prepSourcesOrderByBundle = new LinkedHashMap<>();

    private LinkedHashSet<Class<? extends Annotation>> prepTargets = new LinkedHashSet<>();
    private MapBinder<Class<? extends Annotation>, PatternDao> sources;
    private Multibinder<Class<? extends Annotation>> sourcesDefaultOrder;
    private MapBinder<Class<? extends I18NKey>, LinkedHashSet<Class<? extends Annotation>>> sourcesOrderByBundle;

    private MapBinder<Class<? extends Annotation>, PatternDao> targets;

    @Override
    protected void configure() {


        TypeLiteral<LinkedHashSet<Class<? extends Annotation>>> setOfAnnotationsTypeLiteral = new TypeLiteral<LinkedHashSet<Class<? extends Annotation>>>() {
        };
        TypeLiteral<Class<? extends I18NKey>> keyClassTypeLiteral = new TypeLiteral<Class<? extends I18NKey>>() {
        };


        sourcesDefaultOrder = Multibinder.newSetBinder(binder(), annotationLiteral, PatternSourceOrderDefault.class);
        sources = MapBinder.newMapBinder(binder(), annotationLiteral, patternDaoTypeLiteral, PatternSources.class);
        targets = MapBinder.newMapBinder(binder(), annotationLiteral, patternDaoTypeLiteral, PatternTargets.class);
        sourcesOrderByBundle = MapBinder.newMapBinder(binder(), keyClassTypeLiteral, setOfAnnotationsTypeLiteral, PatternSourceOrderByBundle.class);

        define();


        bindProcessor();


        bindPatternSource();
        bindPatternCacheLoader();
        bindPatternUtility();
        bindFieldScanner();
        bindHostClassIdentifier();
        //        bindDatabaseBundleReader();


        bindSources();
        bindSourcesDefaultOrder();
        bindSourceOrderByBundle();
        bindTargets();
        bindClassPatternDao();
        bindPatternDao();
        bindI18NSourceProvider();
        super.configure(); // This must be at the end
    }


    /**
     * Binds the entries set by calls to {@link #target} (which may be none)
     */
    protected void bindTargets() {
        for (Class<? extends Annotation> entry : prepTargets) {
            Key<PatternDao> key = Key.get(PatternDao.class, entry);
            targets.addBinding(entry)
                    .to(key);
        }
    }

    /**
     * Binds the {@link ClassPatternDao} to its default implementation, override to provide your own implementation
     */
    protected void bindClassPatternDao() {
        bind(ClassPatternDao.class).to(DefaultClassPatternDao.class);
    }

    /**
     * Binds the {@link PatternDao} to the annotation for {@link ClassPatternDao}.   This enables class based I18N patterns to be used, if {@link
     * ClassPatternSource} is included within I18NModule as a source.
     */
    @SuppressWarnings("UninstantiableBinding") // fooled by bindClassPatternDao causing indirection
    protected void bindPatternDao() {
        bind(PatternDao.class).annotatedWith(ClassPatternSource.class)
                .to(ClassPatternDao.class);

    }


    /**
     * Binds sources to {@link PatternDao} classes as defined by {@link #prepSources}, setting {@link ClassPatternDao} as default if nothing
     * defined.
     */
    public void bindSources() {

        if (prepSources.isEmpty()) {
            prepSources.add(ClassPatternSource.class);
        }
        for (Class<? extends Annotation> entry : prepSources) {
            Key<PatternDao> key = Key.get(PatternDao.class, entry);
            sources.addBinding(entry)
                    .to(key);
        }
    }


    /**
     * See javadoc for {@link UnenhancedClassIdentifier} for an explanation of what this is for.  Override this method if you provide your own implementation
     */
    protected void bindHostClassIdentifier() {
        bind(UnenhancedClassIdentifier.class).to(DefaultUnenhancedClassIdentifier.class);
    }

    /**
     * See javadoc for {@link I18NFieldScanner} for an explanation of what this is for.  Override this method if you provide your own implementation
     */
    protected void bindFieldScanner() {
        bind(I18NFieldScanner.class).to(DefaultI18NFieldScanner.class);
    }

    /**
     * See javadoc for {@link PatternUtility} for an explanation of what this is for.  Override this method if you provide your own implementation
     */
    protected void bindPatternUtility() {
        bind(PatternUtility.class).to(DefaultPatternUtility.class);
    }


    /**
     * See javadoc for {@link PatternSourceProvider} for an explanation of what this is for.  Override this method if you provide your own implementation
     */
    protected void bindI18NSourceProvider() {
        bind(PatternSourceProvider.class).to(DefaultPatternSourceProvider.class);
    }

    /**
     * See javadoc for {@link DefaultPatternCacheLoaderConfig} for an explanation of what this is for.  Override this method if you provide your own implementation
     */
    protected void bindPatternCacheLoader() {
        bind(PatternCacheLoader.class).to(DefaultPatternCacheLoader.class);
        bind(PatternCacheLoaderConfig.class).to(PatternCacheLoaderConfigByOption.class);
    }

    /**
     * It is generally advisable to use the same scope for this as for current locale (see {@link #bindCurrentLocale()}.   See javadoc for {@link
     * DefaultPatternSource} for an explanation of what this is for.  Override this method if you provide your own implementation
     */
    protected void bindPatternSource() {
        bind(PatternSource.class).to(DefaultPatternSource.class)
                .in(VaadinSessionScoped.class);
    }


    /**
     * Override this method to provide your own implementation of {@link CurrentLocale} or to change the scope used.
     * Choose between {@link UIScoped} or {@link VaadinSessionScoped}, depending on whether you want users to set the
     * language for each browser tab or each browser instance, respectively.
     */
    protected void bindCurrentLocale() {
        bind(CurrentLocale.class).to(VaadinCurrentLocale.class)
                .in(VaadinSessionScoped.class);
    }

    /**
     * If you don't wish to configure this module from your Binding Manager, sub-class and override this method to define calls to @link
     * #supportedLocales(Locale...)}, @link #defaultLocale(Locale)} etc - then modify your Binding Manager to use your sub-class
     * <p>
     * If you are only using more than one I18N source, the order which you want them accessed needs to be specified using {@link #sourcesDefaultOrder}
     * and/or {@link #sourcesOrderByBundle}.  This is because Guice does not guarantee order if multiple MapBinders are combined (through the use of multiple
     * modules)
     */
    protected void define() {
    }

    /**
     * Override this method to provide your own implementation of {@link I18NProcessor}
     */
    protected void bindProcessor() {
        bind(I18NProcessor.class).to(DefaultI18NProcessor.class);
    }


    protected void bindSourcesDefaultOrder() {
        for (Class<? extends Annotation> source : prepSourcesDefaultOrder) {
            sourcesDefaultOrder.addBinding()
                    .toInstance(source);
        }

    }

    protected void bindSourceOrderByBundle() {
        for (Map.Entry<Class<? extends I18NKey>, LinkedHashSet<Class<? extends Annotation>>> entry : prepSourcesOrderByBundle.entrySet()) {
            sourcesOrderByBundle.addBinding(entry.getKey())
                    .toInstance(entry.getValue());
        }

    }


    /**
     * If you are using one source for I18N, there is no need to use this method
     * <p>
     * However, Guice does not guarantee order if multiple MapBinders are combined (through the use of multiple modules) - the order must then be explicitly
     * specified using this method.
     * <p>
     * This order is used for ALL key classes, unless overridden by {@link #sourcesOrderByBundle}, or by {@link Option} in {@link
     * DefaultPatternSource}
     *
     * @return this for fluency
     */

    @SafeVarargs
    public final VaadinI18NModule sourcesDefaultOrder(@Nonnull Class<? extends Annotation>... sources) {
        checkNotNull(sources);
        Collections.addAll(prepSourcesDefaultOrder, sources);
        return this;
    }

    /**
     * This method sets the order in which to poll the I18N pattern sources, but for a specific bundle (I18NKey class)
     * <p>
     * {@link #sourcesDefaultOrder} applies to all key classes (bundles)
     * <p>
     * <p>
     * If you have only one source - you definitely won't need this method
     *
     * @param keyClass the class of the I18NKey to use (in Java terms the resource 'bundle')
     * @param sources  a set of sources, (or 'formats' in resourceBundle terms).  These should be all, or a subset, of  {@link #sources}
     * @return this for fluency
     */

    @SafeVarargs
    public final VaadinI18NModule sourcesOrderByBundle(@Nonnull Class<? extends I18NKey> keyClass, @Nonnull Class<? extends Annotation>... sources) {
        checkNotNull(keyClass);
        checkNotNull(sources);
        LinkedHashSet<Class<? extends Annotation>> sourceSet = new LinkedHashSet<>(Arrays.asList(sources));
        prepSourcesOrderByBundle.put(keyClass, sourceSet);
        return this;
    }

    /**
     * An I18N target is used when writing out I18N patterns.  Typically this is used either by the auto-stub feature of {@link PatternSource} or the {@link
     * PatternUtility} for moving patterns from one source to another.
     * <p>
     * All the targets which may be required should be added with this method (this ensures the bindings are in place).  The selection of which target to use,
     * if any, is made using the Option settings for {@link PatternSourceProvider}
     * <p>
     * <p>
     * Adds an I18N target, identified by {@code target} (target is roughly equivalent to 'format' in the native Java I18N support, except that it does not
     * imply any particular type of target - it is just an identifier).  Within Krail the target is expected to be the equivalent of a persistence unit
     * <p>
     * Note that if targets are set in multiple Guice modules, they will be merged by Guice into a single set
     *
     * @param target A BindingAnnotation identifying a Persistence Unit (or equivalent) that is providing a DAO as a source
     * @return this for fluency
     */
    public final VaadinI18NModule target(@Nonnull Class<? extends Annotation> target) {
        checkNotNull(target);
        prepTargets.add(target);
        return this;
    }


    /**
     * Adds an I18N source, identified by {@code source} (source is roughly equivalent to 'format' in the native Java I18N support, except that it does not
     * imply any particular type of source - it is just an identifier).  Within Krail the source is expected to be the equivalent of a persistence unit
     *
     * @param source A BindingAnnotation identifying a Persistence Unit (or equivalent) that is providing a DAO as a source
     * @return this for fluency
     */
    public VaadinI18NModule source(@Nonnull Class<? extends Annotation> source) {
        checkNotNull(source);
        prepSources.add(source);
        return this;
    }
}

