package se.omegapoint.facepalm.infrastructure;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.domain.*;
import se.omegapoint.facepalm.domain.repository.ImageRepository;
import se.omegapoint.facepalm.infrastructure.db.ImagePost;
import se.omegapoint.facepalm.infrastructure.db.StoredImage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class JPAImageRepository implements ImageRepository {

    private final EventService eventService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public JPAImageRepository(final EventService eventService) {
        this.eventService = notNull(eventService);
    }

    @Override
    public List<se.omegapoint.facepalm.domain.ImagePost> findAll() {
        final List<ImagePost> images = entityManager.createQuery("SELECT im FROM ImagePost im ORDER BY im.id DESC", ImagePost.class).getResultList();
        return images.stream()
                .map(this::convertToDomain)
                .collect(toList());
    }

    @Override
    public Optional<se.omegapoint.facepalm.domain.ImagePost> findById(String id) {
        notBlank(id);

        final String query = String.format("SELECT * FROM IMAGE_POSTS WHERE id = %s", id);

        eventService.publishEventWith("Searching for image post with query: " + query);

        final List<ImagePost> imagePosts = entityManager.createNativeQuery(query, ImagePost.class).getResultList();

        return imagePosts.size() == 1 ? Optional.of(convertToDomain(imagePosts.get(0))) : Optional.empty();
    }

    @Override
    public void addImagePost(final Title title, final byte[] data) {
        final Session session = entityManager.unwrap(Session.class);
        final Blob blob = Hibernate.getLobCreator(session).createBlob(data);

        final StoredImage storedImage = new StoredImage();
        storedImage.setData(blob);

        final ImagePost imagePost = ImagePost.builder()
                .withPoints(0L)
                .withNumComments(0L)
                .withTitle(title)
                .withStoredImage(storedImage)
                .build();

        entityManager.persist(imagePost);
    }

    @Override
    public Image findImageByPostId(final Long id) {
        notNull(id);

        final List<ImagePost> result = entityManager.createQuery("SELECT im FROM ImagePost im WHERE im.id = :id", ImagePost.class)
                .setParameter("id", id)
                .getResultList();

        if (result.size() != 1) {
            return null;
        }

        final StoredImage storedImage = result.get(0).getStoredImage();
        final Image image;
        try {
            image = new Image(IOUtils.toByteArray(storedImage.getData().getBinaryStream()));
        } catch (Exception e) {
            throw new IllegalStateException("Could not read image from database with ID " + id);
        }

        return image;
    }

    private se.omegapoint.facepalm.domain.ImagePost convertToDomain(final ImagePost i) {
        return new se.omegapoint.facepalm.domain.ImagePost(
                i.getId(),
                new Title(i.getTitle()),
                new NumberOfPoints(i.getPoints()),
                new NumberOfComments(i.getNumComments())
        );
    }
}
