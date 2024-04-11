package com.xoriant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xoriant.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long>{

    Optional<Location> findLocationByStateIdAndId(Long stateId, Long id);
    Optional<List<Location>> findLocationByStateId(Long stateId);
}

