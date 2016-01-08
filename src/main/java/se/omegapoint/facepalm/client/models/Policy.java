package se.omegapoint.facepalm.client.models;

import static org.apache.commons.lang3.Validate.notBlank;

public class Policy {
    public final String text;

    public Policy(final String text) {
        this.text = notBlank(text);
    }
}
