package uk.q3c.krail.i18n;

import uk.q3c.krail.i18n.api.EnumResourceBundle;

import java.util.ResourceBundle;

/**
 * The only reason for this class is that I could not figure out how to achieve the same in Kotlin - generics do
 * not work in the same way
 * <p>
 * Created by David Sowerby on 03 Aug 2017
 */
public class ClassPatternDaoHelper {

    protected String getValue(ResourceBundle bundle, Enum<?> key) {
        EnumResourceBundle enumBundle = (EnumResourceBundle) bundle;
        //noinspection unchecked
        enumBundle.setKeyClass(key.getClass());
        enumBundle.load();
        //noinspection unchecked
        return enumBundle.getValue(key);
    }
}
