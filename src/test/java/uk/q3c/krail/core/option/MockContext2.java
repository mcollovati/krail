package uk.q3c.krail.core.option;

import com.vaadin.data.Property;
import uk.q3c.krail.i18n.TestLabelKey;
import uk.q3c.krail.testutil.option.MockOption;

import javax.annotation.Nonnull;

/**
 * Created by David Sowerby on 03 Aug 2017
 */
public class MockContext2 extends MockContext {
    public static
    final OptionKey<Boolean> key3 = new OptionKey<>(false, MockContext2.class, TestLabelKey.Static, TestLabelKey.Large);
    private static
    final OptionKey<Integer> key4 = new OptionKey<>(126, MockContext2.class, TestLabelKey.Private_Static, TestLabelKey.Large);
    public final OptionKey<Integer> key2 = new OptionKey<>(124, this, TestLabelKey.key2, TestLabelKey.Blank);
    private final OptionKey<Integer> key1 = new OptionKey<Integer>(123, this, TestLabelKey.key1);
    Option option = new MockOption();

    @Nonnull
    @Override
    public Option optionInstance() {
        return option;
    }

    @Override
    public void optionValueChanged(Property.ValueChangeEvent event) {

    }
}