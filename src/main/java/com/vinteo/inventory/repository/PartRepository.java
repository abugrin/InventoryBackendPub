package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.DeviceEntity;
import com.vinteo.inventory.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PartRepository extends JpaRepository<PartEntity, Integer> {
    List<PartEntity> findByDeviceId(Integer device_id);

    List<PartEntity> findByLocationId(Integer location_id);

    Long countByDeviceId(Integer device);

    Long countByType_Id(Integer type_id);


}
