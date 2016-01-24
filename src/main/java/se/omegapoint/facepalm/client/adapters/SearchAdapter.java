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
