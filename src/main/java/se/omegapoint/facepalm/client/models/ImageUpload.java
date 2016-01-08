package se.omegapoint.facepalm.client.models;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class ImageUpload {
    public final String title;
    public final byte[] data;

    public ImageUpload(final String title, final byte[] data) {
        this.title = notBlank(title);
        this.data = notNull(data);
    }
}
