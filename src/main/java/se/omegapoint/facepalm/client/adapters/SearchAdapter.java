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
import se.omegapoint.facepalm.application.UserService;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.SearchQuery;
import se.omegapoint.facepalm.client.models.SearchResultEntry;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;
import se.omegapoint.facepalm.domain.User;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class SearchAdapter {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public SearchAdapter(final UserService userService, final FriendService friendService) {
        this.userService = notNull(userService);
        this.friendService = notNull(friendService);
    }

    public List<SearchResultEntry> findUsersLike(final SearchQuery searchQuery) {
        notNull(searchQuery);

        return userService.searchForUsersLike(searchQuery.value)
                .stream()
                .filter(u -> !u.username.equals(currentUser().userName))
                .map(u -> new SearchResultEntry(u.username, u.email, u.firstname, u.lastname, isFriendsWithCurrentUser(u)))
                .collect(toList());
    }

    private boolean isFriendsWithCurrentUser(final User friend) {
        return friendService.usersAreFriends(currentUser().userName, friend.username);
    }

    private AuthenticatedUser currentUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
