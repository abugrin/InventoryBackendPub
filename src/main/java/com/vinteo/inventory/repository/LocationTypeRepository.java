package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.LocationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationTypeRepository extends JpaRepository<LocationTypeEntity, Integer> {

}
