package uk.q3c.krail.core.option;

import com.vaadin.data.Property;
import uk.q3c.krail.testutil.option.MockOption;

import javax.annotation.Nonnull;

/**
 * Created by David Sowerby on 03 Aug 2017
 */
public class MockContext0 extends MockContext {
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