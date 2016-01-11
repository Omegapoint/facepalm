package se.omegapoint.facepalm.domain.repository;

import se.omegapoint.facepalm.domain.Image;
import se.omegapoint.facepalm.domain.ImagePost;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    List<ImagePost> findAll();

    Optional<ImagePost> findById(String id);

    void addImagePost(String title, byte[] data);

    Image findImageByPostId(Long id);
}
