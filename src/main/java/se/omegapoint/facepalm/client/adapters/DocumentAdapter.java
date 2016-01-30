/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
