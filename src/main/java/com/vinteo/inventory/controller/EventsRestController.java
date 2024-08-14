package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.DeviceTypeEntity;
import com.vinteo.inventory.entity.EventEntity;
import com.vinteo.inventory.model.TypeAddRequest;
import com.vinteo.inventory.model.TypeEditRequest;
import com.vinteo.inventory.service.DeviceTypeService;
import com.vinteo.inventory.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class EventsRestController {

    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/api/events/device/{device_id}")
    public List<EventEntity> deviceEvents(@PathVariable int device_id) {
        return eventService.getByDeviceId(device_id);
    }

    @GetMapping(value = "/api/events/part/{part_id}")
    public List<EventEntity> partEvents(@PathVariable int part_id) {
        return eventService.getByPartId(part_id);
    }

}
