package se.omegapoint.facepalm.application.transfer;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class NewImageComment {
    public final Long imageId;
    public final String author;
    public final String text;

    public NewImageComment(final Long imageId, final String author, final String text) {
        this.imageId = notNull(imageId);
        this.author = notBlank(author);
        this.text = notBlank(text);
    }
}
