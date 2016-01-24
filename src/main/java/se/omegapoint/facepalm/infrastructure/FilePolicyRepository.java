package se.omegapoint.facepalm.infrastructure;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.omegapoint.facepalm.domain.EventService;
import se.omegapoint.facepalm.domain.Policy;
import se.omegapoint.facepalm.domain.repository.PolicyRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class FilePolicyRepository implements PolicyRepository {

    private final EventService eventService;

    @Autowired
    public FilePolicyRepository(final EventService eventService) {
        this.eventService = notNull(eventService);
    }

    @Override
    public Policy retrievePolicyWith(final String filename) {
        notBlank(filename);

        try {
            final String command = format(queryForOperatingSystem(), filename);

            eventService.publishEventWith(format("About to execute command[%s]", command));

            final Process exec = Runtime.getRuntime().exec(command);

            final String text = IOUtils.toString(exec.getInputStream(), Charset.forName("UTF-8"));

            return new Policy(text);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read specified file " + filename);
        }
    }

    private String queryForOperatingSystem() {
        final String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return os.contains("win") ?
                "cmd /K \"cd docs && type %s && exit\"" :
                "cat ./docs/%s";
    }
}
