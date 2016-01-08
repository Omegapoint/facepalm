package se.omegapoint.facepalm.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import se.omegapoint.facepalm.application.PolicyService;
import se.omegapoint.facepalm.client.config.Adapter;
import se.omegapoint.facepalm.client.models.Policy;

import static org.apache.commons.lang3.Validate.notNull;

@Adapter
public class PolicyAdapter {

    private final PolicyService policyService;

    @Autowired
    public PolicyAdapter(final PolicyService policyService) {
        this.policyService = notNull(policyService);
    }

    public Policy retrievePolicy(final String filename) {
        final se.omegapoint.facepalm.domain.Policy policy = policyService.retrievePolicy(filename);
        return new Policy(policy.text);
    }
}
