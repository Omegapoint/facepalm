package se.omegapoint.facepalm.client.models;

public class NewImageComment {
    public String imageId;
    public String text;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(final String imageId) {
        this.imageId = imageId;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
