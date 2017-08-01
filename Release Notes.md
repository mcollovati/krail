### Release Notes for krail 10.0.0.0

This release is a major refactor to extract some elements which can actually stand alone from Krail itself - notably this includes I18N and Options

There will, regrettably be quite a few changes in Krail apps, but with one or two exceptions will be limited to package changes

#### I18N

- generic I18N capability moved from `uk.q3c.krail.core.i18n` to `uk.q3c.krail.i18n.api` and `uk.q3c.krail.i18n` as appropriate, in projects **krail-i18n-api** and **krail-i18n** respectively
- `DefaultCurrentLocale` is now `VaadinCurrentLocale` as it assumes the use of Vaadin.  A generic DefaultCurrentLocale is included in **krail-i18n**
-`uk.q3c.krail.i18n.api.EnumResourceBundle` to  


