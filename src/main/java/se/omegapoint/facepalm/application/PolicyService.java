package se.omegapoint.facepalm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.omegapoint.facepalm.domain.Policy;
import se.omegapoint.facepalm.domain.repository.PolicyRepository;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyService(final PolicyRepository policyRepository) {
        this.policyRepository = notNull(policyRepository);
    }

    public Policy retrievePolicy(final String filename) {
        notBlank(filename);
        return policyRepository.retrievePolicyWith(filename);
    }
}
