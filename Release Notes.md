### Release Notes for krail 10.0.0.0

This release is a major refactor to extract some elements which can actually stand alone from Krail itself - notably this includes I18N and Options

Unfortunately there are a lot of changes which will affect existing Krail apps, many are limited to package changes, but some code changes
were needed to separate Option and I18N

#### General

@Nonnull annotations are gradually being removed - they can cause different results to occur in the IDE than when running in Gradle.
Where such assertions as are required, they will be replaced either by using Kotlin or using Guava's *checkNotNull* (many methods already use the latter)

- `DefaultBindingManager.i18nModule()` changed to `DefaultBindingManager.i18nModules()` and returns a list

#### I18N

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

#### Option

- `OptionContext.getOption` is now `OptionContext.optionInstance()`
- `OptionContext` is parameterised

Moved:

- uk.q3c.krail.option.api
