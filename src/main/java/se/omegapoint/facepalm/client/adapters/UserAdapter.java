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
import se.omegapoint.facepalm.application.Result;
import se.omegapoint.facepalm.application.UserService;
import se.omegapoint.facepalm.application.UserService.UserFailure;
import se.omegapoint.facepalm.application.UserService.UserSuccess;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.Profile;
import se.omegapoint.facepalm.client.models.RegisterCredentials;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;
import se.omegapoint.facepalm.domain.User;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;
import static se.omegapoint.facepalm.client.adapters.OperationResult.failure;
import static se.omegapoint.facepalm.client.adapters.OperationResult.success;

@Adapter
public class UserAdapter {

    private final UserService userService;

    @Autowired
    public UserAdapter(final UserService userService) {
        this.userService = notNull(userService);
    }

    public OperationResult registerNewUser(final RegisterCredentials credentials) {
        notNull(credentials);

        final Result<UserSuccess, UserFailure> result = userService.registerUser(credentials.getUsername(), credentials.getEmail(), credentials.getFirstname(),
                credentials.getLastname(), credentials.getPassword());
        return result.isSuccess() ? success() : failure("User already exists"); // TODO [dh] Proper error mapping!
    }

    public Optional<Profile> profileFor(final String username) {
        final Result<User, UserFailure> result = userService.getUserWith(username);
        return result.isSuccess() ? Optional.of(new Profile(result.success())) : Optional.empty();
    }

    public Profile profileForCurrentUser() {
        final AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Result<User, UserFailure> result = userService.getUserWith(authenticatedUser.userName);
        return new Profile(result.success());
    }
}
