package se.omegapoint.facepalm.client.security;

import static org.apache.commons.lang3.Validate.notBlank;

public class AuthenticatedUser {
    public final String userName;

    public AuthenticatedUser(final String userName) {
        this.userName = notBlank(userName);
    }
}
