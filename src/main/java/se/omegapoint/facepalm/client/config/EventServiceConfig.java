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

package se.omegapoint.facepalm.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.facepalm.domain.event.EventService;
import se.omegapoint.facepalm.infrastructure.event.ApplicationEventLogger;
import se.omegapoint.facepalm.infrastructure.event.ReactorEventService;

import static se.omegapoint.facepalm.infrastructure.event.EventChannel.GLOBAL;

@Configuration
public class EventServiceConfig {
    @Bean
    public EventService eventService() {
        final ReactorEventService eventService = new ReactorEventService(EventBus.create());
        eventService.register(GLOBAL.channel(), new ApplicationEventLogger());
        return eventService;
    }
}
