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

package uk.q3c.krail.core.services

import com.google.inject.Inject
import spock.guice.UseModules
import spock.lang.Specification
import uk.q3c.krail.core.eventbus.EventBusModule
import uk.q3c.krail.core.i18n.KrailI18NConfigModule
import uk.q3c.krail.persist.inmemory.InMemoryModule
import uk.q3c.krail.testutil.guice.uiscope.TestUIScopeModule
import uk.q3c.krail.testutil.guice.vsscope.TestVaadinSessionScopeModule
import uk.q3c.krail.testutil.i18n.TestI18NModule
import uk.q3c.krail.testutil.option.TestOptionModule
import uk.q3c.krail.util.UtilsModule

/**
 * Created by David Sowerby on 16 Dec 2015
 */
@UseModules([ServicesModule, TestI18NModule, EventBusModule, TestVaadinSessionScopeModule, TestUIScopeModule, TestOptionModule, InMemoryModule, UtilsModule, KrailI18NConfigModule])
class ServicesIntegrationTest extends Specification {

    @Inject
    ServicesModel model

    def setup() {

    }


    def "construction"() {
        expect:
        model.registeredServices() != null
    }
}
