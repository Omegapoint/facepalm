package se.omegapoint.facepalm.domain;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class Title {
    private static final int MAN_LENGTH = 100;

    public final String value;

    public Title(final String value) {
        this.value = notBlank(value);
        isTrue(this.value.length() < MAN_LENGTH, "Title can be maximum 100 characters");
    }
}
