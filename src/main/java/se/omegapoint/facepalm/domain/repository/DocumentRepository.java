package se.omegapoint.facepalm.domain.repository;

import se.omegapoint.facepalm.infrastructure.db.Document;

import java.util.List;

public interface DocumentRepository {
    List<Document> findByUsername(String username);
}
