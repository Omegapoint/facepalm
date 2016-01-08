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
