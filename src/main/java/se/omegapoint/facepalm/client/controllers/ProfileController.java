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
