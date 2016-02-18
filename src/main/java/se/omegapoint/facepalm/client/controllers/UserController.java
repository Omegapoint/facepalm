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

package se.omegapoint.facepalm.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.omegapoint.facepalm.client.adapters.OperationResult;
import se.omegapoint.facepalm.client.adapters.UserAdapter;
import se.omegapoint.facepalm.client.models.RegisterCredentials;

import javax.validation.Valid;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class UserController {

    private final UserAdapter userAdapter;

    @Autowired
    public UserController(final UserAdapter userAdapter) {
        this.userAdapter = notNull(userAdapter);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final Model model) {
        if (userIsLoggedIn()) {
            return "redirect:/";
        }

        model.addAttribute("registerCredentials", new RegisterCredentials());
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(final @Valid RegisterCredentials credentials, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            credentials.resetPasswords();
            return "login";
        }

        final OperationResult result = userAdapter.registerNewUser(credentials);
        if (result.isSuccess()) {
            model.addAttribute("message", "User successfully created, please login"); // TODO [dh] Internationalization
            model.addAttribute("registerCredentials", new RegisterCredentials());
            return "login"; // FIXME [dh] Should be redirect with flash attributes, but i simply cannot get it working tonight, why?!
        } else {
            model.addAttribute("registerMessage", result.message());
            credentials.resetPasswords();
            return "login";
        }
    }

    private boolean userIsLoggedIn() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
