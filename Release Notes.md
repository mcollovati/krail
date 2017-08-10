## Release Notes for krail 0.10.0.0

This release is a major refactor to extract some elements which can actually stand alone from Krail itself - notably this includes I18N and Options

Unfortunately there are a LOT of changes which will affect existing Krail apps. Many changes are limited to package changes, but there are some code changes
were needed to separate Option and I18N

### General

@Nonnull annotations are gradually being removed - they can cause different results to occur in the IDE (when applied as runtime assertions) than when running in Gradle.
They will be replaced either by using Kotlin or using Guava's *checkNotNull* (many methods already use the latter)

### Bindings

- `DefaultBindingManager.i18nModule()` changed to `DefaultBindingManager.i18nModules()` and returns a list

### I18N

- generic I18N capability moved from `uk.q3c.krail.core.i18n` to `uk.q3c.krail.i18n.api` and `uk.q3c.krail.i18n` as appropriate, in project **krail-i18n**, Krail specific (mostly Vaadin related) i18n remains in `uk.q3c.krail.core`  
- `DefaultCurrentLocale` in **krail** is now `VaadinCurrentLocale` as it assumes the use of Vaadin.  A generic `DefaultCurrentLocale` added to **krail-i18n**
-`uk.q3c.krail.i18n.api.EnumResourceBundle` moved to **krail-i18n**
- `uk.q3c.util.MessageFormat` moved to `uk.q3c.krail.i18n.MessageFormat`
- `I18NModule` split, new `I18NModule` in **krail-i18n**, `VaadinI18NModule` in core
- contents of `uk.q3c.krail.core.persist.clazz.i18n` moved to `uk.q3c.krail.i18n.clazz` or `uk.q3c.krail.i18n.api.clazz`
- contents of `uk.q3c.krail.core.persist.common.i18n` moved to `uk.q3c.krail.i18n` or `uk.q3c.krail.i18n.api`
- `uk.q3c.util.guava.GuavaCacheConfiguration` moved to `uk.q3c.util.guava`
- `uk.q3c.krail.i18n.I18NHostClassIdentifier` is now `uk.q3c.util.clazz.UnenhancedClassIdentifier`
- contents of `uk.q3c.krail.core.persist.cache.i18n` moved to `uk.q3c.krail.i18n` or `uk.q3c.krail.i18n.api`

#### Translate and MessageFormat

The `MessageFormat` utility class has been deprecated and replaced by interface `MessageFormat2` and implementation `DefaultMessageFormat`.
- `Translate` uses the new implementation 
- `Translate` (and `DefaultMessageFormat` if used directly) offers 3 levels of strictness when handling mis-matches between arguments and parameters, **STRICT**, **STRICT_EXCEPTION** and **LENIENT**. The default, **STRICT**, behaves the same way as the previous version.


### Option
- `AnnotationOptionList` had nothing to do with `Option`.  Renamed `AnnotationList` and moved to `uk.q3c.util.collection`
- `OptionContext.getOption` is now `OptionContext.optionInstance()`
- `OptionContext` is parameterised
- `OptionList` deleted
- Scope of `DefaultOptionCache` is set in `OptionModule` instead of by annotation
- `OptionSource.getContainer()` moved to a new interface `OptionContainerSource` in 'core', to remove Vaadin dependency
- `OptionPermission.Action` is now `OptionEditAction`

### Util
All of `uk.q3c.util` has been reviewed and either allocated to sub-packages or moved to somewhere more appropriate. Those remaining in the util package have also been moved to a separate library, **q3c-util** 

Of particular note:

- `uk.q3c.util.ID` moved to `uk.q3c.krail.core.vaadin.ID`


### Testing

- `TestByteEnhancementModule` replaced by `uk.q3c.util.test.AOPTestModule`

### persist and data

These two packages were quite badly confused.  All classes genuinely related to data manipulation moved to `uk.q3c.util.data` in **q3c-util**.  A number of classes had 'Option' in their name, even though they were not specific to `Option`
These have been renamed to reflect their more general nature.

Generic persistence classes moved to `uk.q3c.krail.persist` or `uk.q3c.krail.persist.api` in **krail-persist** and **krail-persist-api** respectively.

- `AnnotationOptionList` renamed `AnnotationList`
- `AnnotationOptionListConverter` renamed `AnnotationListConverter`
- `OptionConverter` renamed `DataItemConverter` - it is used by `Option` but not specific to it
- `OptionElementConverter` to `DataConverter`
- `AnnotationOptionListConverter` to `AnnotationListConverter`