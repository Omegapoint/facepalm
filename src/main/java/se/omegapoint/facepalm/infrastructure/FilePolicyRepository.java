package se.omegapoint.facepalm.infrastructure;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import se.omegapoint.facepalm.domain.Policy;
import se.omegapoint.facepalm.domain.repository.PolicyRepository;

import java.io.IOException;
import java.util.Locale;

import static org.apache.commons.lang3.Validate.notBlank;

@Repository
public class FilePolicyRepository implements PolicyRepository {
    @Override
    public Policy retrievePolicyWith(final String filename) {
        notBlank(filename);

        try {
            final String command = String.format(queryForOperatingSystem(), filename);
            final Process exec = Runtime.getRuntime().exec(command);

            final String text = IOUtils.toString(exec.getInputStream());
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
