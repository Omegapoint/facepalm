package se.omegapoint.facepalm.infrastructure.db;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE_POSTS")
public class ImagePost {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_ID", nullable = false)
    private StoredImage storedImage;

    @Column(name = "NUM_COMMENTS", nullable = false)
    private Long numComments;

    @Column(name = "POINTS", nullable = false)
    private Long points;

    public ImagePost() {
        // JPA..
    }

    public ImagePost(final String title, final StoredImage storedImage, final Long numComments, final Long points) {
        this.title = title;
        this.storedImage = storedImage;
        this.numComments = numComments;
        this.points = points;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoredImage getStoredImage() {
        return storedImage;
    }

    public void setStoredImage(final StoredImage storedImage) {
        this.storedImage = storedImage;
    }

    public Long getNumComments() {
        return numComments;
    }

    public void setNumComments(Long numComments) {
        this.numComments = numComments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public static class Builder {
        private String title;
        private StoredImage storedImage;
        private Long numComments;
        private Long points;

        public Builder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder withStoredImage(final StoredImage storedImage) {
            this.storedImage = storedImage;
            return this;
        }

        public Builder withNumComments(final Long numComments) {
            this.numComments = numComments;
            return this;
        }

        public Builder withPoints(final Long points) {
            this.points = points;
            return this;
        }

        public ImagePost build() {
            return new ImagePost(title, storedImage, numComments, points);
        }
    }
}
