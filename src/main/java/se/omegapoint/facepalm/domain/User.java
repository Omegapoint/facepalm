package se.omegapoint.facepalm.domain;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public class User {
    public final String username;
    public final String email;
    public final String firstname;
    public final String lastname;

    /*
        Is there a better way than just using strings I wonder?
     */
    public User(final String username, final String email, final String firstname, final String lastname) {
        this.username = notBlank(username);
        this.email = notBlank(email);
        this.firstname = notBlank(firstname);
        this.lastname = notBlank(lastname);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(lastname, user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, firstname, lastname);
    }
}
