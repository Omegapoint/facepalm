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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;
import reactor.fn.Consumer;

import static org.apache.commons.lang3.Validate.notNull;

public final class ApplicationEventLogger implements Consumer<Event<GenericEvent>> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void accept(final Event<GenericEvent> event) {
        notNull(event);
        logger.info(event.getData().message());
    }
}
