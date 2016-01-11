package se.omegapoint.facepalm.domain;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class NumberOfPoints {
    public final Long value;

    public NumberOfPoints(final Long value) {
        this.value = notNull(value);
        isTrue(value >= 0);
    }
}