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
