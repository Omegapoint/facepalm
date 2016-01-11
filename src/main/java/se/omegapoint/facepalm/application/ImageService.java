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

        commentRepository.addComment(new ImageComment(newImageComment.imageId, newImageComment.author, newImageComment.text));
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
