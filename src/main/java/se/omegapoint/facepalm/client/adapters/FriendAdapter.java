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

package se.omegapoint.facepalm.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import se.omegapoint.facepalm.application.FriendService;
import se.omegapoint.facepalm.application.Result;
import se.omegapoint.facepalm.application.UserService;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.Friend;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;
import se.omegapoint.facepalm.domain.User;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class FriendAdapter {

    private final FriendService friendService;
    private final UserService userService;

    @Autowired
    public FriendAdapter(final FriendService friendService, final UserService userService) {
        this.friendService = notNull(friendService);
        this.userService = notNull(userService);
    }

    public Set<Friend> friendsForCurrentUser() {
        return friendService.friendFor(currentUser())
                .stream()
                .map(i -> new Friend(i.username, i.firstname, i.lastname))
                .collect(toSet());
    }

    public void addFriend(final String friendUsername) {
        final Result<User, UserService.UserFailure> friend = userService.getUserWith(friendUsername);
        if (friend.isFailure()) {
            return; // TODO [dh] Should probably propogate some form of error here
        }

        final boolean alreadyFriends = friendService.usersAreFriends(currentUser(), friendUsername);
        if (!alreadyFriends) {
            friendService.addFriend(currentUser(), friendUsername);
        }
    }

    private String currentUser() {
        final AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser.userName;
    }
}
