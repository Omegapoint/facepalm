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
import se.omegapoint.facepalm.client.models.ImageComment;
import se.omegapoint.facepalm.client.models.ImagePost;
import se.omegapoint.facepalm.client.models.ImageUpload;
import se.omegapoint.facepalm.domain.Image;

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
