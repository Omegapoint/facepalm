/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
