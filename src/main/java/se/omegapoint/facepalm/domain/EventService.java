package se.omegapoint.facepalm.domain;

import se.omegapoint.facepalm.infrastructure.event.ApplicationEvent;

public interface EventService {
    void publish(ApplicationEvent event);
}
