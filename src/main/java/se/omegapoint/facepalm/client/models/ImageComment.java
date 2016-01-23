package se.omegapoint.facepalm.client.models;

import static org.apache.commons.lang3.Validate.notBlank;

public class ImageComment {
    public final String author;
    public final String text;

    public ImageComment(final String author, final String text) {
        this.author = notBlank(author);
        this.text = notBlank(text);
    }
}
