package uk.q3c.krail.core.option;

import uk.q3c.krail.option.api.Option;
import uk.q3c.krail.option.test.MockContext;
import uk.q3c.krail.option.test.MockOption;

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
    public void optionValueChanged(Object event) {

    }
}