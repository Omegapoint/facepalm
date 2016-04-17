package se.omegapoint.facepalm.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.omegapoint.facepalm.client.config.DatabaseConfig;
import se.omegapoint.facepalm.domain.NewUserCredentials;
import se.omegapoint.facepalm.domain.User;
import se.omegapoint.facepalm.domain.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class, JPAUserRepositoryTest.DbConf.class}, initializers = ConfigFileApplicationContextInitializer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JPAUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void should_register_new_user() {
        final NewUserCredentials credentials = new NewUserCredentials("admin", "admin@op.com", "Admin", "Adminsson", "pass");
        userRepository.addUser(credentials);

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "pass");

        assertTrue(user.isPresent());
        assertEquals(credentials.username, user.get().username);
        assertEquals(credentials.firstname, user.get().firstname);
        assertEquals(credentials.lastname, user.get().lastname);
        assertEquals(credentials.email, user.get().email);
    }

    @Test
    @Transactional
    public void should_fetch_user_with_correct_credentials() {
        final se.omegapoint.facepalm.infrastructure.db.User entity = userWithNameAndPassword("admin", "pass");
        entityManager.persist(entity);

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "pass");

        assertTrue(user.isPresent());
    }

    @Test
    @Transactional
    public void should_not_find_user_with_incorrect_credentials() {
        final se.omegapoint.facepalm.infrastructure.db.User entity = userWithNameAndPassword("admin", "pass");
        entityManager.persist(entity);

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "bad_pass");

        assertFalse(user.isPresent());
    }

    @Test
    @Transactional
    public void should_allow_sql_injection_attack() {
        final se.omegapoint.facepalm.infrastructure.db.User entity = userWithNameAndPassword("admin", "pass");
        entityManager.persist(entity);

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "' OR 1=1 --'");

        assertTrue(user.isPresent());
    }

    private se.omegapoint.facepalm.infrastructure.db.User userWithNameAndPassword(final String username, final String password) {
        final se.omegapoint.facepalm.infrastructure.db.User entity = new se.omegapoint.facepalm.infrastructure.db.User();
        entity.setUsername(username);
        entity.setFirstname("Admin");
        entity.setLastname("Adminsson");
        entity.setEmail("email@example.com");
        entity.setPassword(password);
        return entity;
    }

    @Configuration
    public static class DbConf {
        @Bean
        public UserRepository userRepository() {
            return new JPAUserRepository(event -> {
            });
        }
    }
}