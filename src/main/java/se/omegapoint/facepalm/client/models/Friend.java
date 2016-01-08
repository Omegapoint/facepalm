package se.omegapoint.facepalm.client.models;

import static org.apache.commons.lang3.Validate.notBlank;

public class Friend {
    public final String username;
    public final String firstname;
    public final String lastname;

    public Friend(final String username, final String firstname, final String lastname) {
        this.username = notBlank(username);
        this.firstname = notBlank(firstname);
        this.lastname = notBlank(lastname);
    }
}
