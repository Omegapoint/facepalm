package se.omegapoint.facepalm.infrastructure.event;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import se.omegapoint.facepalm.domain.EventService;

import static org.apache.commons.lang3.Validate.notNull;

public class ReactorEventService implements EventService {

    private final EventBus eventBus;

    public ReactorEventService(final EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publish(final Object event) {
        notNull(event);
        eventBus.notify(EventChannel.GLOBAL.channel, Event.wrap(event));
    }

    public void register(final Consumer<Event<Object>> eventListener) {
        notNull(eventListener);
        eventBus.on(EventChannel.GLOBAL.selector(), eventListener);
    }
}

