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

package se.omegapoint.facepalm.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.domain.ImageComment;
import se.omegapoint.facepalm.domain.event.EventService;
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
