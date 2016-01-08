package se.omegapoint.facepalm.infrastructure;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.domain.ImageComment;
import se.omegapoint.facepalm.domain.repository.CommentRepository;
import se.omegapoint.facepalm.infrastructure.db.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional
public class JPACommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ImageComment> findByImageId(final Long id) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE imageId = :id", Comment.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .map(c -> new ImageComment(c.getImageId(), c.getAuthor(), c.getComment()))
                .collect(toList());
    }

    @Override
    public void addComment(final ImageComment comment) {
        entityManager.persist(new Comment(comment.imageId, comment.author, comment.text));
    }
}
