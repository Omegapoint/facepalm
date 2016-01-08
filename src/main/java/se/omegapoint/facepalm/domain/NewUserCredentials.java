package se.omegapoint.facepalm.domain;

import static org.apache.commons.lang3.Validate.notBlank;

public class NewUserCredentials {
    public final String username;
    public final String email;
    public final String firstname;
    public final String lastname;
    public final String password;

    public NewUserCredentials(final String username, final String email, final String firstname, final String lastname, final String password) {
        this.username = notBlank(username);
        this.email = notBlank(email);
        this.firstname = notBlank(firstname);
        this.lastname = notBlank(lastname);
        this.password = notBlank(password);
    }
}
