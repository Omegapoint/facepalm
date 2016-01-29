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
