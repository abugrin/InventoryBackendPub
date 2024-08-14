package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.*;
import com.vinteo.inventory.model.ItemAddRequest;
import com.vinteo.inventory.model.ItemEditRequest;
import com.vinteo.inventory.repository.*;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
public class EquipmentService {

    protected LocationRepository locationRepository;
    protected EventRepository eventRepository;
    protected EventService eventService;
    protected DeviceTypeRepository typeRepository;
    protected EqStatusRepository statusRepository;
    protected DeviceRepository deviceRepository;
    protected PartRepository partRepository;


    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
    @Autowired
    public void setTypeRepository(DeviceTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }
    @Autowired
    public void setStatusRepository(EqStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Autowired
    public void setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    @Autowired
    public void setPartRepository(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    protected void addItem(ItemEntity item, ItemAddRequest request, UserEntity user) {
        item.setName(request.getName());
        item.setPn(request.getPn());
        item.setSerial(request.getSerial());
        item.setDescription(request.getDescription());
        item.setCreateDate(Instant.now());
        item.setUpdateDate(Instant.now());
        item.setUpdateUser(user);
        typeRepository.findById(request.getTypeId()).ifPresent(item::setType);
        locationRepository.findById(request.getLocationId()).ifPresent(item::setLocation);
        statusRepository.findById(0).ifPresent(item::setStatus);
        EventEntity event = eventService.addEvent(item, user);

        if (item instanceof DeviceEntity) {
            deviceRepository.save((DeviceEntity) item);
        } else if (item instanceof PartEntity) {
            partRepository.save((PartEntity) item);
        }

        eventRepository.save(event);


        log.info("Item added: ID: " + item.getId() + " Name: " + item.getName());
    }

    protected void saveItem(ItemEntity item, ItemEditRequest request, UserEntity user) {
        String changes = trackChanges(item, request);
        item.setName(request.getName());
        item.setSerial(request.getSerial());
        item.setPn(request.getPn());
        item.setDescription(request.getDescription());
        if (item.getType().getId() != request.getTypeId()) {
            typeRepository.findById(request.getTypeId()).ifPresent(item::setType);
        }
        if (item.getStatus().getId() != request.getStatusId()) {
            statusRepository.findById(request.getStatusId()).ifPresent(item::setStatus);
        }
        EventEntity event = eventService.createEvent(item, user, changes);
        if (item instanceof DeviceEntity) {
            deviceRepository.save((DeviceEntity) item);
        } else if (item instanceof PartEntity) {
            partRepository.save((PartEntity) item);
        }

        eventRepository.save(event);
        log.info("Item updated: ID: " + item.getId() + " Name: " + item.getName());
    }


    private String trackChanges(ItemEntity item, ItemEditRequest request) {
        StringBuilder changes = new StringBuilder();

        if (!item.getName().equals(request.getName())) {
            changes.append("Название ").append(item.getName()).append(" > ").append(request.getName()).append("\n");
        }
        if (!item.getSerial().equals(request.getSerial())) {
            changes.append("SN ").append(item.getSerial()).append(" > ").append(request.getSerial()).append("\n");
        }
        if (!item.getPn().equals(request.getPn())) {
            changes.append("PN ").append(item.getPn()).append(" > ").append(request.getPn()).append("\n");
        }
        if (item.getStatus().getId() != request.getStatusId()) {
            changes.append("Статус ").append(item.getStatus().getId()).append(" > ").append(request.getStatusId()).append("\n");
        }
        if (!item.getDescription().equals(request.getDescription())) {
            changes.append("Описание изменено").append("\n");
        }
        if (item.getType().getId() != request.getTypeId()) {
            changes.append("Тип изменен ").append(item.getType().getId()).append(" > ").append(request.getTypeId()).append("\n");
        }

        return changes.toString();
    }
    long calculateDuration(EventEntity event) {
        long duration = 0;
        try {
            if (event != null) {
                Instant moveDate = event.getDate();
                Instant todayDate = Instant.now();
                duration = Duration.between(moveDate, todayDate).toDays();
            }
        } catch (NoResultException ignore) {

        }
        return duration;
    }

}
