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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.facepalm.client.adapters.PolicyAdapter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class PolicyController {

    private final PolicyAdapter policyAdapter;

    @Autowired
    public PolicyController(final PolicyAdapter policyAdapter) {
        this.policyAdapter = notNull(policyAdapter);
    }

    @RequestMapping(value = "policy", method = RequestMethod.GET)
    public String policies(final @RequestParam(value = "file", required = false) String filename, final Model model) {
        if (isBlank(filename)) {
            return "policies";
        }

        model.addAttribute("fileContents", policyAdapter.retrievePolicy(filename));
        return "policies";
    }
}
