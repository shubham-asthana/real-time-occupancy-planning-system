package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;

import com.planning.occupancy.model.Policy;

public class PolicyRepository {

    private final List<Policy> policies = DataLoader.getDataLoader().getPolicies();

    public List<Policy> findAll() {
        return Collections.unmodifiableList(policies);
    }
}
