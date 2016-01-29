package se.omegapoint.facepalm.client.models;

public class Document {
    public final String filename;
    public final Long fileSize;
    public final String date;

    public Document(final String filename, final Long fileSize, final String date) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.date = date;
    }
}
