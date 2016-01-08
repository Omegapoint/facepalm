package se.omegapoint.facepalm.infrastructure;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.domain.repository.DocumentRepository;
import se.omegapoint.facepalm.infrastructure.db.Document;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Repository
@Transactional
public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Document> findByUsername(String username) {
        notNull(username);

        return entityManager.createQuery("SELECT d FROM Document d WHERE username = :name", Document.class)
                .setParameter("name", username)
                .getResultList();
    }
}
