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

package se.omegapoint.facepalm.domain.repository;

import se.omegapoint.facepalm.domain.NewUserCredentials;
import se.omegapoint.facepalm.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {
    Optional<User> findByUsername(final String username);

    Set<User> findFriendsFor(String username);

    Optional<User> findByNameAndPassword(final String username, final String password);

    List<User> findLike(String value);

    void addFriend(String user, String friend);

    void addUser(NewUserCredentials credentials);

    boolean usersAreFriends(String userA, String userB);
}
