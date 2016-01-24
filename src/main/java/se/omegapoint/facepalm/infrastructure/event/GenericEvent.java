package se.omegapoint.facepalm.infrastructure.event;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class GenericEvent implements ApplicationEvent {
    private final Object data;

    public GenericEvent(final Object data) {
        this.data = notNull(data);
    }

    @Override
    public String message() {
        return data.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenericEvent that = (GenericEvent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
