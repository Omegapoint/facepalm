package se.omegapoint.facepalm.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;
import reactor.fn.Consumer;

public final class EventLogger implements Consumer<Event<Object>> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void accept(final Event<Object> event) {
        logger.info(event.getData().toString());
    }
}
