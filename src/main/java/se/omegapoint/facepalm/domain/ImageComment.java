package se.omegapoint.facepalm.domain;

public class ImageComment {

    public final Long imageId;
    public final String author;
    public final String text;

    public ImageComment(final Long imageId, final String author, final String text) {
        this.imageId = imageId;
        this.author = author;
        this.text = text;
    }

    @Override
    public String toString() {
        return "ImageComment{" +
                "imageId=" + imageId +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
