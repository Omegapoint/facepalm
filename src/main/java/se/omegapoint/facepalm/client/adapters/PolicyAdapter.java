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

package se.omegapoint.facepalm.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import se.omegapoint.facepalm.application.PolicyService;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.Policy;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class PolicyAdapter {

    private final PolicyService policyService;

    @Autowired
    public PolicyAdapter(final PolicyService policyService) {
        this.policyService = notNull(policyService);
    }

    public Policy retrievePolicy(final String filename) {
        final Optional<se.omegapoint.facepalm.domain.Policy> policy = policyService.retrievePolicy(filename);
        return policy.map(p -> new Policy(p.text)).orElseGet(() -> new Policy("Could not load policy"));
    }
}
