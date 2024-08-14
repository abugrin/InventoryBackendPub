package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.EventActionEntity;
import com.vinteo.inventory.entity.EventObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventObjectRepository extends JpaRepository<EventObjectEntity, Integer> {
}
