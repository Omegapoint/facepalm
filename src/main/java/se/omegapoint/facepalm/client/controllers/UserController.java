package se.omegapoint.facepalm.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
}
