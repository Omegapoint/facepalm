package se.omegapoint.facepalm.client.controllers;

import com.google.common.collect.ImmutableMap;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import se.omegapoint.facepalm.client.models.User;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class SimpleErrorController implements ErrorController {

    private static final String PATH = "/error";
    private static final Map<Integer, String> ERROR_TEXTS = ImmutableMap.of(
            404, "The page you were looking for those not exist"
            // Rest is default
    );
    private static final String DEFAULT_ERROR_MESSAGE = "Oops! An error has occurred. Please contact admin if the problem persists and ask them to check the logs.";

    @RequestMapping(value = PATH)
    public String error(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse, final Model model) {
        model.addAttribute("user", user());
        model.addAttribute("errorText", mapFromStatus(servletResponse.getStatus()));
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    public User user() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            final AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new User(authenticatedUser);
        }
        return null;
    }

    private String mapFromStatus(final int status) {
        return ERROR_TEXTS.getOrDefault(status, DEFAULT_ERROR_MESSAGE);
    }
}