package se.omegapoint.facepalm.client.models;

import se.omegapoint.facepalm.domain.User;

import static org.apache.commons.lang3.Validate.notNull;

public class Profile {
    public String email;
    public String username;
    public String firstname;
    public String lastname;

    public Profile(final User user) {
        notNull(user);
        this.username = user.username;
        this.email = user.email;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
    }
}
