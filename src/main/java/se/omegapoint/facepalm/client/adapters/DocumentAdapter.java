package se.omegapoint.facepalm.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import se.omegapoint.facepalm.application.DocumentService;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class DocumentAdapter {
    private DocumentService documentService;

    @Autowired
    public DocumentAdapter(final DocumentService documentService) {
        this.documentService = notNull(documentService);
    }

    public List<Document> documentsFor(final String user) {
        notBlank(user);

        return documentService.documentsForUser(user).stream()
                .map(d -> new Document(d.filename, d.fileSize, date(d.uploadedDate)))
                .collect(toList());
    }

    private String date(final LocalDateTime uploadedDate) {
        return uploadedDate != null ? uploadedDate.format(DateTimeFormatter.BASIC_ISO_DATE) : "";
    }
}
