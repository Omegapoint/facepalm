package se.omegapoint.facepalm.domain.repository;

import se.omegapoint.facepalm.domain.Policy;

public interface PolicyRepository {
    Policy retrievePolicyWith(String filename);
}
