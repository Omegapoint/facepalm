package se.omegapoint.facepalm.infrastructure.event;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.fn.Consumer;
import se.omegapoint.facepalm.domain.EventService;

import static org.apache.commons.lang3.Validate.notNull;

public class ReactorEventService implements EventService {

    private final EventBus eventBus;

    public ReactorEventService(final EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publishEventWith(final Object data) {
        notNull(data);
        eventBus.notify(EventChannel.GLOBAL.channel, Event.wrap(data));
    }

    public void register(final Consumer<Event<Object>> eventListener, final Selector channel) {
        notNull(eventListener);
        eventBus.on(channel, eventListener);
    }
}

