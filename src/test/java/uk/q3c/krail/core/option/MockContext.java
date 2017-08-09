package uk.q3c.krail.core.option;

import uk.q3c.krail.option.api.Option;
import uk.q3c.krail.option.api.OptionContext;
import uk.q3c.krail.testutil.option.MockOption;

import javax.annotation.Nonnull;

/**
 * Created by David Sowerby on 03 Aug 2017
 */
class MockContext implements OptionContext<Object> {

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
