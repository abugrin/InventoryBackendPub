package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.EqStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EqStatusRepository extends JpaRepository<EqStatusEntity, Integer> {

}
