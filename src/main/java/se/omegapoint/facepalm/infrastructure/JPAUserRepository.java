package se.omegapoint.facepalm.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.domain.EventService;
import se.omegapoint.facepalm.domain.NewUserCredentials;
import se.omegapoint.facepalm.domain.repository.UserRepository;
import se.omegapoint.facepalm.infrastructure.db.Friendship;
import se.omegapoint.facepalm.infrastructure.db.User;
import se.omegapoint.facepalm.infrastructure.event.GenericEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class JPAUserRepository implements UserRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(JPAUserRepository.class);
    private final EventService eventService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public JPAUserRepository(final EventService eventService) {
        this.eventService = notNull(eventService);
    }

    @Override
    public Optional<se.omegapoint.facepalm.domain.User> findByUsername(final String username) {
        notBlank(username);

        eventService.publishEventWith(format("Searching for username[%s]", username));

        return findUserFromUsername(username)
                .map(this::convertUserToDomain);
    }

    @Override
    public Optional<se.omegapoint.facepalm.domain.User> findByNameAndPassword(final String username, final String password) {
        notNull(username);
        notNull(password);

        final String query = format("SELECT * FROM ACCOUNTS WHERE USERNAME = '%s' AND PASSWORD = '%s'", username, password);

        final List<User> users = entityManager.createNativeQuery(query, User.class).getResultList();

        eventService.publishEventWith(users.isEmpty() ?
                format("No matching user with username[%s], password[%s]", username, password) :
                format("Found matching users with username[%s], password[%s]", username, password)
        );

        return users.isEmpty() ?
                Optional.empty() :
                Optional.of(convertUserToDomain(users.get(0)));
    }

    @Override
    public List<se.omegapoint.facepalm.domain.User> findLike(final String value) {
        notBlank(value);
        final String query = "SELECT u FROM User u WHERE LOWER(u.username)  LIKE :query OR " +
                "                                        LOWER(u.firstname) LIKE :query OR " +
                "                                        LOWER(u.lastname)  LIKE :query    ";

        return entityManager.createQuery(query, User.class)
                .setParameter("query", likeify(value))
                .getResultList()
                .stream()
                .map(this::convertUserToDomain)
                .collect(toList());
    }

    @Override
    public Set<se.omegapoint.facepalm.domain.User> findFriendsFor(final String username) {
        notBlank(username);

        eventService.publishEventWith(new GenericEvent(format("Searching for friends with username[%s]", username)));

        final List<User> friends = entityManager.createNativeQuery("" +
                "SELECT * FROM ACCOUNTS WHERE USERNAME IN                " +
                " (                                                      " +
                "   SELECT USER_ID FROM FRIENDSHIPS WHERE FRIEND_ID = :a " +
                "   UNION                                                " +
                "   SELECT FRIEND_ID FROM FRIENDSHIPS WHERE USER_ID = :a " +
                " )                                                      ", User.class)
                .setParameter("a", username)
                .getResultList();

        return friends.stream().map(this::convertUserToDomain).collect(toSet());
    }

    @Override
    public void addUser(final NewUserCredentials credentials) {
        notNull(credentials);

        eventService.publishEventWith(credentials);

        entityManager.persist(new User(credentials.username, credentials.email, credentials.firstname, credentials.lastname, credentials.password));
    }

    @Override
    public boolean usersAreFriends(final String userA, final String userB) {
        notBlank(userA);
        notBlank(userB);

        final Long count = entityManager.createQuery("SELECT COUNT(f.id) FROM Friendship f WHERE (f.user = :a AND f.friend = :b) OR (f.user = :a AND f.friend = :b)", Long.class)
                .setParameter("a", userA)
                .setParameter("b", userB)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public void addFriend(final String user, final String friendToAdd) {
        notBlank(user);
        notBlank(friendToAdd);

        eventService.publishEventWith(format("User[%s] is now friend of [%s]", user, friendToAdd));

        entityManager.persist(new Friendship(user, friendToAdd, Date.valueOf(LocalDate.now())));
    }

    private Optional<User> findUserFromUsername(final String username) {
        final List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    private se.omegapoint.facepalm.domain.User convertUserToDomain(final User user) {
        return new se.omegapoint.facepalm.domain.User(user.getUsername(), user.getEmail(), user.getFirstname(), user.getLastname());
    }

    private String likeify(final String query) {
        return "%" + query.toLowerCase() + "%";
    }
}
