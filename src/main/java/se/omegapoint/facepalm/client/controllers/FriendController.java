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
import se.omegapoint.facepalm.client.adapters.FriendAdapter;

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class FriendController {

    private final FriendAdapter friendAdapter;

    @Autowired
    public FriendController(final FriendAdapter friendAdapter) {
        this.friendAdapter = notNull(friendAdapter);
    }

    @RequestMapping("/friends")
    public String friends(final Model model) {
        model.addAttribute("friends", friendAdapter.friendsForCurrentUser());
        return "friends";
    }

    @RequestMapping("/addfriend")
    public String addFriend(final @RequestParam(name = "friend") String friend) {
        friendAdapter.addFriend(friend);
        return format("redirect:/profile?user=%s", friend);
    }
}
