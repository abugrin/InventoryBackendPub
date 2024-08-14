package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.*;
import com.vinteo.inventory.model.ItemAddRequest;
import com.vinteo.inventory.model.ItemEditRequest;
import com.vinteo.inventory.model.MoveRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequestScope
public class PartService extends EquipmentService {

    private List<PartEntity> partList;

    @PostConstruct
    void init() {
        partList = partRepository.findAll();

        for (PartEntity part : partList) {
            part.setDaysAtCustomer(getDaysAtCustomer(part.getId()));
            part.setPartsCount(partRepository.countByDeviceId(part.getId()));
        }
    }

    public List<PartEntity> getAllStoreParts(int typeId) {
        List<PartEntity> storeParts = partList.stream().filter(part -> part.getLocation().getType().getId().equals(LocationTypeEntity.STORE)).collect(Collectors.toList());
        return filterByType(storeParts, typeId);
    }

    public List<PartEntity> getAllCustomerParts(int typeId) {
        List<PartEntity> customerParts = partList.stream().filter(device -> device.getLocation().getType().getId().equals(LocationTypeEntity.CUSTOMER)).toList();
        return filterByType(customerParts, typeId);
    }

    public List<PartEntity> getAllPartnerParts(int typeId) {
        List<PartEntity> partnerParts = partList.stream().filter(device -> device.getLocation().getType().getId().equals(LocationTypeEntity.PARTNER)).toList();
        return filterByType(partnerParts, typeId);
    }

    @SuppressWarnings("unused")
    public List<PartEntity> getAllStoreParts() {
        return getAllStoreParts(0);
    }
    @SuppressWarnings("unused")
    public List<PartEntity> getAllCustomerParts() {
        return getAllCustomerParts(0);
    }
    @SuppressWarnings("unused")
    public List<PartEntity> getAllPartnerParts() {
        return getAllPartnerParts(0);
    }


    public Long countByType(Integer type_id) {
        return partRepository.countByType_Id(type_id);
    }

    public List<PartEntity> getAllParts(int typeId) {
        return filterByType(partList, typeId);
    }


    private List<PartEntity> filterByType(List<PartEntity> parts, int typeId) {
        if (typeId == 0) {
            return parts;
        } else {
            return parts.stream().filter(part -> part.getType().getId() == typeId).toList();
        }
    }

    public long getDaysAtCustomer(int partId) {
        EventEntity event = eventRepository.findFirstByPartIdAndActionIdOrderByIdDesc(partId, (long) 3);
        return calculateDuration(event);

    }

    public List<PartEntity> getAllDeviceParts(Integer device_id) {
        return partRepository.findByDeviceId(device_id);
    }

    public Optional<PartEntity> getById(int partId) {
        return partRepository.findById(partId);
    }

    public void uninstallPart(int partId, UserEntity user) {
        partRepository.findById(partId).ifPresent(part -> {
            eventRepository.save(eventService.createEvent(part, user, "Компонент удален из " + part.getDevice().getName()));
            part.setDevice(null);
            partRepository.save(part);
        });
    }

    public List<PartEntity> getAvailableParts(int deviceId) {
        Optional<DeviceEntity> device = deviceRepository.findById(deviceId);
        return findPartsFor(device.orElseThrow());

    }

    private List<PartEntity> findPartsFor(DeviceEntity device) {
        List<PartEntity> parts = partRepository.findByLocationId(device.getLocation().getId());
        return parts.stream()
                .filter(part -> part.getDevice() == null)
                .filter(part -> Objects.equals(part.getType().getId(), device.getType().getId()))
                .toList();

    }

    public void movePart(MoveRequest moveRequest, UserEntity user) {
        partRepository.findById(moveRequest.getId()).ifPresent(part -> movePart(part, moveRequest, user));

    }

    private void movePart(PartEntity part, MoveRequest request, UserEntity user) {
        Optional<LocationEntity> locationOptional = locationRepository.findById(request.getLocationTo());

        locationOptional.ifPresent(location -> {
            EventEntity event = eventService.moveEvent(part, user, location);
            part.setLocation(location);
            part.setUpdateDate(Instant.now());
            part.setUpdateUser(user);
            partRepository.save(part);
            eventRepository.save(event);
        });

    }

    public void addPart(ItemAddRequest partRequest, UserEntity user) {
        PartEntity part = new PartEntity();
        addItem(part, partRequest, user);
    }

    public void updatePart(ItemEditRequest partRequest, UserEntity user) {
        Optional<PartEntity> partOptional = partRepository.findById(partRequest.getId());
        partOptional.ifPresent(part -> saveItem(part, partRequest, user));
    }
}

