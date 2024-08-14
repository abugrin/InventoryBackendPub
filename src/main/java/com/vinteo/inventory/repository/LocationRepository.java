package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.DeviceEntity;
import com.vinteo.inventory.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    //List<LocationEntity> findById(Integer location_id);
    List<LocationEntity> findByTypeId(Integer type_id);

}
