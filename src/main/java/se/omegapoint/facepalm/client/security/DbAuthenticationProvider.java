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

package se.omegapoint.facepalm.client.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import se.omegapoint.facepalm.domain.User;
import se.omegapoint.facepalm.domain.repository.UserRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.notNull;

public class DbAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    public DbAuthenticationProvider(final UserRepository userRepository) {
        this.userRepository = notNull(userRepository);
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String username = (String) token.getPrincipal();
        final String password = (String) token.getCredentials();

        final Optional<User> user = userRepository.findByNameAndPassword(username, password);

        return user.map(u -> new UsernamePasswordAuthenticationToken(new AuthenticatedUser(u.username), null, emptyList()))
                .orElse(null);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
