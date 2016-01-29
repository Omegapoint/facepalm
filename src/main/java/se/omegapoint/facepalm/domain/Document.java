package se.omegapoint.facepalm.domain;

import java.time.LocalDateTime;

public class Document {
    public final Long id;
    public final String filename;
    public final Long fileSize;
    public final LocalDateTime uploadedDate;

    public Document(final Long id, final String filename, final Long fileSize, final LocalDateTime uploadedDate) {
        this.id = id;
        this.filename = filename;
        this.fileSize = fileSize;
        this.uploadedDate = uploadedDate;
    }
}
