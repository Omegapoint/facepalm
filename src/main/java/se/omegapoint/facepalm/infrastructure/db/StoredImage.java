package se.omegapoint.facepalm.infrastructure.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Blob;

@Entity
@Table(name = "STORED_IMAGES")
public class StoredImage {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Lob
    @Column(name = "DATA", columnDefinition = "blob")
    private Blob data;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Blob getData() {
        return data;
    }

    public void setData(final Blob data) {
        this.data = data;
    }
}
