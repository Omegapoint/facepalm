package se.omegapoint.facepalm.domain;

import static org.apache.commons.lang3.Validate.notNull;

public class Image {
    public final byte[] data;

    public Image(final byte[] data) {
        this.data = notNull(data);
    }
}
