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
