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

import spock.lang.Specification
import uk.q3c.krail.core.i18n.LabelKey

class ServiceKeyTest extends Specification {

    def "to String"() {
        given:
        ServiceKey key = new ServiceKey(LabelKey.Yes)

        expect:
        key.toString().equals("Yes")
    }

    def "equals"() {
        given:
        ServiceKey key1 = new ServiceKey(LabelKey.Yes)
        ServiceKey key2 = new ServiceKey(LabelKey.Yes)
        ServiceKey key3 = new ServiceKey(LabelKey.Yes)
        ServiceKey key4 = new ServiceKey(LabelKey.No)

        expect:
        key1.equals(key2)
        key2.equals(key3)
        !key2.equals(key4)
    }



}
