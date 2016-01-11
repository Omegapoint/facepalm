package se.omegapoint.facepalm.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import se.omegapoint.facepalm.client.adapters.ImageAdapter;
import se.omegapoint.facepalm.client.models.ImageUpload;
import se.omegapoint.facepalm.domain.Image;
import se.omegapoint.facepalm.domain.ImageComment;
import se.omegapoint.facepalm.domain.ImagePost;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class ImageController {

    @Autowired
    private ImageAdapter imageAdapter;

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("posts", imageAdapter.getTopImagePosts());
        return "index";
    }

    @RequestMapping("/image")
    public String image(final @RequestParam String id, final Model model) {
        final Optional<ImagePost> image = imageAdapter.getImage(id);
        if (!image.isPresent()) {
            return "redirect:/404";
        }

        final List<ImageComment> comments = imageAdapter.getCommentsForImage(image.get().id);
        model.addAttribute("image", image.get());
        model.addAttribute("comments", comments);
        return "image";
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String addComment(final @RequestParam("imageId") String imageId, final @RequestParam("text") String text) {
        imageAdapter.addComment(imageId, text);
        return "redirect:/image?id=" + imageId;
    }

    @RequestMapping(value = "/postImage", method = RequestMethod.POST)
    public String postImage(final @RequestParam("title") String title, final @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                final byte[] data = notNull(file.getBytes());
                imageAdapter.addImage(new ImageUpload(title, data));
            } catch (IOException e) {
                throw new IllegalArgumentException("Illegal file upload!");
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/img/{id}")
    public ResponseEntity<byte[]> image(final @PathVariable("id") String id) {
        final Image image = imageAdapter.fetchImage(Long.valueOf(id));

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.data);
    }
}
