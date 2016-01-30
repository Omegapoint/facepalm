package se.omegapoint.facepalm.client.config;

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
    }

    private void uploadImageFileWithTitle(final String title, final String filePath) {
        try {
            final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            imageAdapter.addImage(new ImageUpload(title, IOUtils.toByteArray(new BufferedInputStream(is))));
        } catch (Exception e) {
            LOGGER.error("Unable to create image with title " + title);
        }
    }
}
