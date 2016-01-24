package se.omegapoint.facepalm.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NewUserCredentials that = (NewUserCredentials) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, firstname, lastname, password);
    }

    @Override
    public String toString() {
        return "NewUserCredentials{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
