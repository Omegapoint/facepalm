package se.omegapoint.facepalm.client.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import se.omegapoint.facepalm.domain.EventService;
import se.omegapoint.facepalm.domain.User;
import se.omegapoint.facepalm.domain.repository.UserRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.notNull;
import static se.omegapoint.facepalm.client.security.Login.successfulLoginWith;
import static se.omegapoint.facepalm.client.security.Login.unsuccessfulLoginWith;

public class DbAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final EventService eventService;

    public DbAuthenticationProvider(final UserRepository userRepository, final EventService eventService) {
        this.userRepository = notNull(userRepository);
        this.eventService = eventService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String username = (String) token.getPrincipal();
        final String password = (String) token.getCredentials();

        final Optional<User> user = userRepository.findByNameAndPassword(username, password);

        return user.map(u -> {
            final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(new AuthenticatedUser(u.username), null, emptyList());
            eventService.publish(successfulLoginWith(username, password));
            return result;
        }).orElseGet(() -> {
            eventService.publish(unsuccessfulLoginWith(username, password));
            return null;
        });
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
