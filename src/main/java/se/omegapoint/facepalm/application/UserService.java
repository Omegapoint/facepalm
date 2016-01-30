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

package se.omegapoint.facepalm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.omegapoint.facepalm.domain.NewUserCredentials;
import se.omegapoint.facepalm.domain.User;
import se.omegapoint.facepalm.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notBlank;
import static se.omegapoint.facepalm.application.UserService.UserFailure.USER_ALREADY_EXISTS;
import static se.omegapoint.facepalm.application.UserService.UserFailure.USER_DOES_NOT_EXIST;
import static se.omegapoint.facepalm.application.UserService.UserSuccess.USER_REGISTERD;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Result<UserSuccess, UserFailure> registerUser(final String username, final String email, final String firstname, final String lastname, final String password) {
        notBlank(username);
        notBlank(email);
        notBlank(firstname);
        notBlank(lastname);
        notBlank(password);

        final NewUserCredentials credentials = new NewUserCredentials(username, email, firstname, lastname, password);
        final Optional<User> user = getUser(credentials.username);

        if (user.isPresent()) {
            return Result.failure(USER_ALREADY_EXISTS);
        }

        userRepository.addUser(credentials);
        return Result.success(USER_REGISTERD);
    }

    public Result<User, UserFailure> getUserWith(final String username) {
        notBlank(username);

        final Optional<User> user = getUser(username);
        return user.isPresent() ? Result.success(user.get()) : Result.failure(USER_DOES_NOT_EXIST);
    }

    public List<User> searchForUsersLike(final String query) {
        notBlank(query);
        return userRepository.findLike(query);
    }

    private Optional<User> getUser(final String username) {
        notBlank(username);
        return userRepository.findByUsername(username);
    }

    public enum UserSuccess {
        USER_REGISTERD;
    }

    public enum UserFailure {
        USER_DOES_NOT_EXIST,
        USER_ALREADY_EXISTS;
    }
}
