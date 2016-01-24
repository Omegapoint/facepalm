package se.omegapoint.facepalm.infrastructure.event;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.fn.Consumer;
import se.omegapoint.facepalm.domain.EventService;

import static org.apache.commons.lang3.Validate.notNull;
import static se.omegapoint.facepalm.infrastructure.event.EventChannel.GLOBAL;

public class ReactorEventService implements EventService {

    private final EventBus eventBus;

    public ReactorEventService(final EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publishEventWith(final ApplicationEvent event) {
        notNull(event);
        eventBus.notify(GLOBAL.channel, Event.wrap(event));
    }

    public void publishEventWith(final Object data) {
        notNull(data);
        publishEventWith(new GenericEvent(data));
    }

    public <E extends ApplicationEvent> void register(final Selector channel, final Consumer<Event<E>> eventListener) {
        notNull(eventListener);
        notNull(channel);
        eventBus.on(channel, eventListener);
    }
}

