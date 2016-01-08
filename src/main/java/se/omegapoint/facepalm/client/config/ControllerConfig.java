package se.omegapoint.facepalm.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import se.omegapoint.facepalm.application.CommercialService;
import se.omegapoint.facepalm.client.models.User;
import se.omegapoint.facepalm.client.security.AuthenticatedUser;
import se.omegapoint.facepalm.domain.Commercial;

import java.util.List;

@ControllerAdvice
public class ControllerConfig {

    @Autowired
    CommercialService commercialService;

    @ModelAttribute("user")
    public User user() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            final AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new User(authenticatedUser);
        }
        return null;
    }

    @ModelAttribute("commercials")
    public List<Commercial> commercials() {
        return commercialService.getCommercials();
    }
}
