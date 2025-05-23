package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;
import com.planning.occupancy.model.DeskAssignmentRule;

public class DeskAssignmentRuleRepository {

    private final List<DeskAssignmentRule> deskAssignmentRules = DataLoader.getDataLoader().getDeskAssignmentRules();

    public List<DeskAssignmentRule> findAll() {
        return Collections.unmodifiableList(deskAssignmentRules);
    }
}
