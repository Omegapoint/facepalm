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
