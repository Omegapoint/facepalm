package se.omegapoint.facepalm.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.domain.EventService;
import se.omegapoint.facepalm.domain.ImageComment;
import se.omegapoint.facepalm.domain.repository.CommentRepository;
import se.omegapoint.facepalm.infrastructure.db.Comment;
import se.omegapoint.facepalm.infrastructure.event.GenericEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class JPACommentRepository implements CommentRepository {

    private final EventService eventService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public JPACommentRepository(final EventService eventService) {
        this.eventService = notNull(eventService);
    }

    @Override
    public List<ImageComment> findByImageId(final Long id) {
        eventService.publish(new GenericEvent(format("Searching for image with id[%s]", id)));
        return entityManager.createQuery("SELECT c FROM Comment c WHERE imageId = :id", Comment.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .map(c -> new ImageComment(c.getImageId(), c.getAuthor(), c.getComment()))
                .collect(toList());
    }

    @Override
    public void addComment(final Long imageId, final String author, final String text) {
        debug(imageId, author, text);
        entityManager.persist(new Comment(imageId, author, text));
    }

    private void debug(final Long imageId, final String author, final String text) {
        eventService.publish(new GenericEvent(new StringBuilder().append(imageId).append(":").append(author).append(":").append(text)));
    }
}
