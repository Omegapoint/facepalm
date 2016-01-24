package se.omegapoint.facepalm.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.facepalm.domain.EventService;
import se.omegapoint.facepalm.infrastructure.event.EventLogger;
import se.omegapoint.facepalm.infrastructure.event.ReactorEventService;

import static se.omegapoint.facepalm.infrastructure.event.EventChannel.GLOBAL;

@Configuration
public class EventServiceConfig {
    @Bean
    public EventService eventService() {
        final ReactorEventService eventService = new ReactorEventService(EventBus.create());
        eventService.register(GLOBAL.channel(), new EventLogger());
        return eventService;
    }
}
