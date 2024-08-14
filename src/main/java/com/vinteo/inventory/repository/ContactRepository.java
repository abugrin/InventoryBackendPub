package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {
    List<ContactEntity> findAllByLocationId(Integer location_id);
}
