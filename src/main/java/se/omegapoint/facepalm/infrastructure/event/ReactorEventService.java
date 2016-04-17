/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.omegapoint.facepalm.infrastructure.event;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.fn.Consumer;
import se.omegapoint.facepalm.domain.event.ApplicationEvent;
import se.omegapoint.facepalm.domain.event.EventService;

import static org.apache.commons.lang3.Validate.notNull;
import static se.omegapoint.facepalm.infrastructure.event.EventChannel.GLOBAL;

public class ReactorEventService implements EventService {

    private final EventBus eventBus;

    public ReactorEventService(final EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publish(final ApplicationEvent event) {
        notNull(event);
        eventBus.notify(GLOBAL.channel, Event.wrap(event));
    }

    public <E extends ApplicationEvent> void register(final Selector channel, final Consumer<Event<E>> eventListener) {
        notNull(eventListener);
        notNull(channel);
        eventBus.on(channel, eventListener);
    }
}

