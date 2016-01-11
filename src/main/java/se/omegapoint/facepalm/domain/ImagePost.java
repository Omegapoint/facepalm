package se.omegapoint.facepalm.domain;

import static org.apache.commons.lang3.Validate.notNull;

public class ImagePost {
    public final Long id;
    public final Title title;
    public final NumberOfPoints numPoints;
    public final NumberOfComments numComments;

    public ImagePost(final Long id, final Title title, final NumberOfPoints points, final NumberOfComments numComments) {
        this.id = notNull(id);
        this.title = notNull(title);
        this.numPoints = notNull(points);
        this.numComments = notNull(numComments);
    }
}
