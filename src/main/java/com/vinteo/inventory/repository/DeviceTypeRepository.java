package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.DeviceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceTypeEntity, Integer> {

}
