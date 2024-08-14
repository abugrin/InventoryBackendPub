package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
    List<DeviceEntity> findByLocationId(Integer location_id);

    Long countByType_Id(Integer type_id);

    Long countByLocationId(Integer location_id);
}
