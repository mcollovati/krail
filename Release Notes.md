### Release Notes for krail 10.0.0.0

This release is a major refactor to extract some elements which can actually stand alone from Krail itself - notably this includes I18N and Options

There will, regrettably be quite a few changes in Krail apps, but with one or two exceptions will be limited to package changes

#### I18N

- generic I18N capability moved from `uk.q3c.krail.core.i18n` to `uk.q3c.krail.i18n.api` and `uk.q3c.krail.i18n` as appropriate, in project **krail-i18n** 
- `DefaultCurrentLocale` in **krail** is now `VaadinCurrentLocale` as it assumes the use of Vaadin.  A generic `DefaultCurrentLocale` added to **krail-i18n**
-`uk.q3c.krail.i18n.api.EnumResourceBundle` moved to **krail-i18n**
- `uk.q3c.util.MessageFormat` moved to `uk.q3c.krail.i18n.MessageFormat`
- `I18NModule` split, new `I18NModule` in **krail-i18n**, `VaadinI18NModule` in core
- contents of `uk.q3c.krail.core.persist.clazz.i18n` moved to `uk.q3c.krail.i18n.clazz` or `uk.q3c.krail.i18n.api.clazz`
- contents of `uk.q3c.krail.core.persist.common.i18n` moved to `uk.q3c.krail.i18n` or `uk.q3c.krail.i18n.api`


