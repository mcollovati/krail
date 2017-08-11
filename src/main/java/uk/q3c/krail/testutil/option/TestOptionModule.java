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

package uk.q3c.krail.testutil.option;

import uk.q3c.krail.core.option.KrailOptionModule;
import uk.q3c.krail.i18n.DefaultMessageFormat;
import uk.q3c.krail.i18n.api.MessageFormat2;
import uk.q3c.krail.option.DefaultOptionCache;
import uk.q3c.krail.option.api.Option;
import uk.q3c.krail.option.api.OptionCache;
import uk.q3c.krail.option.test.MockOption;
import uk.q3c.krail.persist.inmemory.InMemory;

/**
 * Created by David Sowerby on 05/12/14.
 */
public class TestOptionModule extends KrailOptionModule {
    @Override
    protected void configure() {
        super.configure();
        bind(MessageFormat2.class).to(DefaultMessageFormat.class);
    }

    public TestOptionModule() {
        activeSource(InMemory.class);
    }

    @Override
    protected void bindOption() {
        bind(Option.class).to(MockOption.class);
    }

    @Override
    protected void bindOptionCache() {
        bind(OptionCache.class).to(DefaultOptionCache.class);
    }


    @Override
    protected void bindOptionPopup() {
    }
}
