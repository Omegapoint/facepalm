package se.omegapoint.facepalm.domain;

public class ImagePost {

    public final Long id;
    public final String title;
    public final Long numPoints;
    public final Long numComments;

    public ImagePost(final se.omegapoint.facepalm.infrastructure.db.ImagePost image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.numComments = image.getNumComments();
        this.numPoints = image.getPoints();
    }
}
