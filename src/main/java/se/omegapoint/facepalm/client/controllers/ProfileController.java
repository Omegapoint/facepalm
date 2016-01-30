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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.facepalm.client.adapters.UserAdapter;
import se.omegapoint.facepalm.client.models.Profile;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class ProfileController {
    public static final String PATH = "/profile";

    private final UserAdapter userAdapter;

    @Autowired
    public ProfileController(final UserAdapter userAdapter) {
        this.userAdapter = notNull(userAdapter);
    }

    @RequestMapping(value = PATH)
    public String profile(final @RequestParam(value = "user", required = false) String username, final Model model) {
        if (username == null) {
            final Profile profile = userAdapter.profileForCurrentUser();
            model.addAttribute("profile", profile);
        } else {
            final Optional<Profile> profile = userAdapter.profileFor(username);
            if (profile.isPresent()) {
                model.addAttribute("profile", profile.get());
            } else {
                return "redirect:/404";
            }
        }
        return "profile";
    }
}
