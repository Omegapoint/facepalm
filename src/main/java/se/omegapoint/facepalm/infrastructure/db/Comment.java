package se.omegapoint.facepalm.infrastructure.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "COMMENT")
    private String comment;

    public Comment() {
        // JPA
    }

    public Comment(final Long imageId, final String author, final String comment) {
        this.imageId = notNull(imageId);
        this.author = notBlank(author);
        this.comment = notBlank(comment);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}