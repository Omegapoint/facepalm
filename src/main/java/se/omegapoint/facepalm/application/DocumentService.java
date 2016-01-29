package se.omegapoint.facepalm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.omegapoint.facepalm.domain.Document;
import se.omegapoint.facepalm.domain.repository.DocumentRepository;

import java.util.List;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(final DocumentRepository documentRepository) {
        this.documentRepository = notNull(documentRepository);
    }

    public List<Document> documentsForUser(final String user) {
        notBlank(user);

        return documentRepository.findAllFor(user);
    }
}
