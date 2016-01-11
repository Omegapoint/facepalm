package se.omegapoint.facepalm.domain;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

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

    public ImagePost(final Long id, final String title, final Long points, final Long numComments) {
        this.id = notNull(id);
        this.title = notBlank(title);
        this.numPoints = notNull(points);
        this.numComments = notNull(numComments);
    }
}
