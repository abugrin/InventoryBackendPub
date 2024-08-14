package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    List<EventEntity> findFirst5ByOrderByIdDesc();

    List<EventEntity> findFirst5ByAction_IdOrderByIdDesc(Long action_id);

    List<EventEntity> findByDeviceIdOrderByIdDesc(Integer device);

    List<EventEntity> findByPartIdOrderByIdDesc(Integer part);

    EventEntity findFirstByDeviceIdAndActionIdOrderByIdDesc(Integer device, Long action);

    EventEntity findFirstByPartIdAndActionIdOrderByIdDesc(Integer part, Long action);

}
