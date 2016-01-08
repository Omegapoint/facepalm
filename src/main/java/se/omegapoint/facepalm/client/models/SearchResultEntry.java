package se.omegapoint.facepalm.client.models;

import static org.apache.commons.lang3.Validate.notBlank;

public class SearchResultEntry {
    public final String username;
    public final String email;
    public final String firstname;
    public final String lastname;
    public final boolean isFriend;

    public SearchResultEntry(final String username, final String email, final String firstname, final String lastname, final boolean isFriend) {
        this.username = notBlank(username);
        this.email = notBlank(email);
        this.firstname = notBlank(firstname);
        this.lastname = notBlank(lastname);
        this.isFriend = isFriend;
    }
}
