package se.omegapoint.facepalm.domain.repository;

import se.omegapoint.facepalm.domain.Document;

import java.util.List;

public interface DocumentRepository {
    List<Document> findAllFor(String user);
}
