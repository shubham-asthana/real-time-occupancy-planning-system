package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.planning.occupancy.model.Space;

public class SpaceRepository {

    private final List<Space> spaces = DataLoader.getDataLoader().getSpaces();

    public List<Space> findAll() {
        return Collections.unmodifiableList(spaces);
    }

    public Optional<Space> findByName(String name) {
        return spaces.stream()
                .filter(space -> space.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Space> findByParentId(String parentId) {
        return spaces.stream()
                .filter(space -> space.getParentId().equalsIgnoreCase(parentId))
                .collect(Collectors.toList());
    }
}
