package se.omegapoint.facepalm.client.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class RegisterCredentials {

    @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Length(min = 2, max = 40, message = "Firstname must be between 2 and 40 characters")
    private String firstname;

    @Length(min = 2, max = 40, message = "Firstname must be between 2 and 40 characters")
    private String lastname;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void resetPasswords() {
        password = null;
    }
}
