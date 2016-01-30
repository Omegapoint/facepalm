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

package se.omegapoint.facepalm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.omegapoint.facepalm.application.transfer.NewImageComment;
import se.omegapoint.facepalm.domain.Image;
import se.omegapoint.facepalm.domain.ImageComment;
import se.omegapoint.facepalm.domain.ImagePost;
import se.omegapoint.facepalm.domain.Title;
import se.omegapoint.facepalm.domain.repository.CommentRepository;
import se.omegapoint.facepalm.domain.repository.ImageRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<ImagePost> getTopImages() {
        return imageRepository.findAll();
    }

    public Optional<ImagePost> getImagePost(final String id) {
        notBlank(id);

        return imageRepository.findById(id);
    }

    public List<ImageComment> getCommentsForImage(final Long id) {
        notNull(id);

        return commentRepository.findByImageId(id).stream().collect(toList());
    }

    public void addComment(final NewImageComment newImageComment) {
        notNull(newImageComment);

        commentRepository.addComment(newImageComment.imageId, newImageComment.author, newImageComment.text);
    }

    public void addImagePost(final Title title, final byte[] data) {
        notNull(title);
        notNull(data);
        imageRepository.addImagePost(title, data);
    }

    public Image getImageFor(final Long id) {
        notNull(id);
        return imageRepository.findImageByPostId(id);
    }
}
