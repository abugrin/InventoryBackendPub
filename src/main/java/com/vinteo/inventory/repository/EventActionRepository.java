package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.EventActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventActionRepository extends JpaRepository<EventActionEntity, Integer> {
}
