package se.omegapoint.facepalm.domain.repository;

import se.omegapoint.facepalm.domain.ImageComment;

import java.util.List;

public interface CommentRepository {
    List<ImageComment> findByImageId(final Long id);

    void addComment(Long imageId, String author, String text);
}
