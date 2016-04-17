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
import se.omegapoint.facepalm.client.config.DatabaseConfig;
import se.omegapoint.facepalm.domain.NewUserCredentials;
import se.omegapoint.facepalm.domain.User;
import se.omegapoint.facepalm.domain.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class, JPAUserRepositoryTest.DbConf.class}, initializers = ConfigFileApplicationContextInitializer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JPAUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void should_fetch_user_with_correct_credentials() {
        userRepository.addUser(new NewUserCredentials("admin", "admin@op.com", "Admin", "Adminsson", "pass"));

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "pass");

        assertTrue(user.isPresent());
    }

    @Test
    public void should_not_find_user_with_incorrect_credentials() {
        userRepository.addUser(new NewUserCredentials("admin", "admin@op.com", "Admin", "Adminsson", "pass"));

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "bad_pass");

        assertFalse(user.isPresent());
    }

    @Test
    public void should_allow_sql_injection_attack() {
        userRepository.addUser(new NewUserCredentials("admin", "admin@op.com", "Admin", "Adminsson", "pass"));

        final Optional<User> user = userRepository.findByNameAndPassword("admin", "' OR 1=1 --'");

        assertTrue(user.isPresent());
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