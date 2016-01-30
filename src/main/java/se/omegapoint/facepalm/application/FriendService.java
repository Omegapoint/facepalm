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

package se.omegapoint.facepalm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.omegapoint.facepalm.domain.User;
import se.omegapoint.facepalm.domain.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class FriendService {

    private final UserRepository userRepository;

    @Autowired
    public FriendService(final UserRepository userRepository) {
        this.userRepository = notNull(userRepository);
    }

    public Set<User> friendFor(final String username) {
        return userRepository.findFriendsFor(username);
    }

    public boolean usersAreFriends(final String userA, final String userB) {
        notBlank(userA);
        notBlank(userB);
        return userRepository.usersAreFriends(userA, userB);
    }

    public void addFriend(final String username, final String friendUsername) {
        notBlank(username);
        notBlank(friendUsername);

        final Optional<User> user = getUser(username);
        final Optional<User> friend = getUser(friendUsername);

        if (user.isPresent() && friend.isPresent()) {
            userRepository.addFriend(user.get().username, friend.get().username);
        }
    }

    private Optional<User> getUser(final String username) {
        notBlank(username);
        return userRepository.findByUsername(username);
    }
}
