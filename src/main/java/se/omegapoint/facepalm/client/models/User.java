package se.omegapoint.facepalm.client.models;

import se.omegapoint.facepalm.client.security.AuthenticatedUser;

import static org.apache.commons.lang3.Validate.notNull;

public class User {
    public final String username;

    public User(final AuthenticatedUser authenticatedUser) {
        notNull(authenticatedUser);
        this.username = authenticatedUser.userName;
    }
}
