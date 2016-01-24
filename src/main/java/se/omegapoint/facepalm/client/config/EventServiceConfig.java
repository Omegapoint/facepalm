package se.omegapoint.facepalm.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.facepalm.domain.EventService;
import se.omegapoint.facepalm.infrastructure.event.EventLogger;
import se.omegapoint.facepalm.infrastructure.event.ReactorEventService;

@Configuration
public class EventServiceConfig {
    @Bean
    public EventService eventService() {
        final ReactorEventService eventService = new ReactorEventService(EventBus.create());
        eventService.register(new EventLogger());
        return eventService;
    }
}
