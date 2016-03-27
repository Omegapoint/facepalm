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

package se.omegapoint.facepalm.client.config;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.client.adapters.ImageAdapter;
import se.omegapoint.facepalm.client.models.ImageUpload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * This class bootstraps some data which cannot be easily added via import.sql
 */
@Service
public class Bootstrapper implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrapper.class);

    private final ImageAdapter imageAdapter;
    private final Environment env;

    @Autowired
    public Bootstrapper(final ImageAdapter imageAdapter, final Environment env) {
        this.imageAdapter = notNull(imageAdapter);
        this.env = notNull(env);
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        if (Boolean.valueOf(env.getProperty("bootstrap.data"))) {
            LOGGER.debug("Started bootstrapping of image posts");
            uploadImageFileWithTitle("He is right behind me, isn't he?", "samples/a.jpg");
            uploadImageFileWithTitle("I cant believe it doesn't work this way", "samples/b.jpg");
            uploadImageFileWithTitle("Mhmm.. pie", "samples/c.jpg");
            LOGGER.debug("Done with bootstrapping");
        }

        createDocumentsIfNotPresent();
    }

    private void uploadImageFileWithTitle(final String title, final String filePath) {
        try {
            final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            imageAdapter.addImage(new ImageUpload(title, IOUtils.toByteArray(new BufferedInputStream(is))));
        } catch (Exception e) {
            LOGGER.error("Unable to create image with title " + title);
        }
    }

    private void createDocumentsIfNotPresent() {
        File documentsFolder = new File("./docsds");
        if (!documentsFolder.isDirectory()) {
            LOGGER.info("Creating missing documents folder with sample text files...");
            documentsFolder.mkdir();
            createDocument(documentsFolder, "security.txt", "This is our security policy! Quite empty though...");
            createDocument(documentsFolder, "advertising.txt", "You are allowed to advertise by emailing us.");
            createDocument(documentsFolder, "general.txt", "Welcome to Facepalm! Find friends or upload an image.");
            createDocument(documentsFolder, "copyright.txt", "Take what you want and run with it!");
        }
    }

    private void createDocument(final File documentsFolder, final String filename, final String content) {
        final File security = new File(documentsFolder.getPath() + "/" + filename);
        try {
            FileUtils.writeStringToFile(security, content);
        } catch (IOException e) {
            LOGGER.error("Could not generate sample text file: " + e.getMessage());
            throw new IllegalStateException("Could not create sample documents");
        }
    }
}
