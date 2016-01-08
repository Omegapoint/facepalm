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
