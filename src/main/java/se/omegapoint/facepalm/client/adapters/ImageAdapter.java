package se.omegapoint.facepalm.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import se.omegapoint.facepalm.application.ImageService;
import se.omegapoint.facepalm.application.transfer.NewImageComment;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.ImageComment;
import se.omegapoint.facepalm.client.models.ImagePost;
import se.omegapoint.facepalm.client.models.ImageUpload;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;
import se.omegapoint.facepalm.domain.Image;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class ImageAdapter {

    private final ImageService imageService;

    @Autowired
    public ImageAdapter(final ImageService imageService) {
        this.imageService = notNull(imageService);
    }


    public List<ImagePost> getTopImagePosts() {
        return imageService.getTopImages().stream()
                .map(this::imagePost)
                .collect(toList());
    }

    public Optional<ImagePost> getImage(final String id) {
        notBlank(id);

        return imageService.getImagePost(id).map(this::imagePost);
    }

    public List<ImageComment> getCommentsForImage(final Long imageId) {
        notNull(imageId);

        return imageService.getCommentsForImage(imageId).stream()
                .map(c -> new ImageComment(c.author, c.text))
                .collect(toList());
    }

    public void addComment(final String imageId, final String text) {
        notBlank(imageId);
        notBlank(text);

        imageService.addComment(new NewImageComment(Long.valueOf(imageId), currentUser(), text));
    }

    public void addImage(final ImageUpload imageUpload) {
        notNull(imageUpload);
        imageService.addImagePost(imageUpload.title, imageUpload.data);
    }

    public Image fetchImage(final Long id) {
        notNull(id);
        return imageService.getImageFor(id);
    }

    public String currentUser() {
        final AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser.userName;
    }

    private ImagePost imagePost(final se.omegapoint.facepalm.domain.ImagePost image) {
        return new ImagePost(image.id, image.title, image.numPoints, image.numComments);
    }
}
