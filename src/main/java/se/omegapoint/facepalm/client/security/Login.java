package se.omegapoint.facepalm.client.security;

import static java.lang.String.format;

class Login {
    private final String value;

    private Login(final String value) {
        this.value = value;
    }

    public static Login successfulLoginWith(final String username, final String password) {
        return new Login(format("Login successful with username[%s], password[%s]", username, password));
    }

    public static Login unsuccessfulLoginWith(final String username, final String password) {
        return new Login(format("Login unsuccessful with username[%s], password[%s]", username, password));
    }

    @Override
    public String toString() {
        return value;
    }
}
