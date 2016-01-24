package se.omegapoint.facepalm.domain;

import se.omegapoint.facepalm.infrastructure.event.ApplicationEvent;

public interface EventService {
    void publishEventWith(Object data);

    void publishEventWith(ApplicationEvent event);
}
