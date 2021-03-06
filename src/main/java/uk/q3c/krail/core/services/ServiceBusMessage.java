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

package uk.q3c.krail.core.services;

import uk.q3c.krail.core.eventbus.BusMessage;

/**
 * A message sent via the event bus relating to a {@link Service}
 *
 * Created by David Sowerby on 10/03/15.
 */
public class ServiceBusMessage implements BusMessage {
    private final Service service;
    private final Service.State fromState;
    private final Service.State toState;
    private Service.Cause cause;

    public ServiceBusMessage(Service service, Service.State fromState, Service.State toState, Service.Cause cause) {
        this.service = service;
        this.fromState = fromState;
        this.toState = toState;
        this.cause = cause;
    }

    public Service.Cause getCause() {
        return cause;
    }

    public Service getService() {
        return service;
    }

    public Service.State getFromState() {
        return fromState;
    }

    public Service.State getToState() {
        return toState;
    }
}
