package se.omegapoint.facepalm.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import se.omegapoint.facepalm.application.FriendService;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.Friend;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class FriendAdapter {

    private final FriendService friendService;

    @Autowired
    public FriendAdapter(final FriendService friendService) {
        this.friendService = notNull(friendService);
    }

    public Set<Friend> friendsForCurrentUser() {
        return friendService.friendFor(currentUser())
                .stream()
                .map(i -> new Friend(i.username, i.firstname, i.lastname))
                .collect(toSet());
    }

    public void addFriend(final String friend) {
        // TODO [dh] Check if user exists first!
        final boolean alreadyFriends = friendService.usersAreFriends(currentUser(), friend);
        if (!alreadyFriends) {
            friendService.addFriend(currentUser(), friend);
        }
    }

    private String currentUser() {
        final AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser.userName;
    }
}
