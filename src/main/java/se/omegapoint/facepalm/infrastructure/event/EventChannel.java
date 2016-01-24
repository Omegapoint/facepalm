package se.omegapoint.facepalm.infrastructure.event;

import reactor.bus.selector.Selector;

import static reactor.bus.selector.Selectors.$;

public enum EventChannel {
    GLOBAL("all");

    public final String channel;

    EventChannel(final String channel) {
        this.channel = channel;
    }

    public Selector selector() {
        return $(channel);
    }
}
