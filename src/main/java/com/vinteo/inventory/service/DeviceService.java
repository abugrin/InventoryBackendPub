package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.*;
import com.vinteo.inventory.model.ItemAddRequest;
import com.vinteo.inventory.model.ItemEditRequest;
import com.vinteo.inventory.model.MoveRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequestScope
public class DeviceService extends EquipmentService {

    private List<DeviceEntity> deviceList;
    private PartService partService;

    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @PostConstruct
    void init() {
        deviceList = deviceRepository.findAll();

        for (DeviceEntity device : deviceList) {
            device.setDaysAtCustomer(getDaysAtCustomer(device.getId()));
            device.setPartsCount(partRepository.countByDeviceId(device.getId()));
        }
    }

    public List<DeviceEntity> getAllStoreDevices(int typeId) {
        List<DeviceEntity> storeDevices = deviceList.stream().filter(device -> device.getLocation().getType().getId().equals(LocationTypeEntity.STORE)).collect(Collectors.toList());
        return filterByType(storeDevices, typeId);
    }

    public List<DeviceEntity> getAllCustomerDevices(int typeId) {
        List<DeviceEntity> customerDevices = deviceList.stream().filter(device -> device.getLocation().getType().getId().equals(LocationTypeEntity.CUSTOMER)).toList();
        return filterByType(customerDevices, typeId);
    }

    public List<DeviceEntity> getAllPartnerDevices(int typeId) {
        List<DeviceEntity> partnerDevices = deviceList.stream().filter(device -> device.getLocation().getType().getId().equals(LocationTypeEntity.PARTNER)).toList();
        return filterByType(partnerDevices, typeId);
    }

    @SuppressWarnings("unused")
    public List<DeviceEntity> getAllStoreDevices() {
        return getAllStoreDevices(0);
    }
    @SuppressWarnings("unused")
    public List<DeviceEntity> getAllCustomerDevices() {
        return getAllCustomerDevices(0);
    }
    @SuppressWarnings("unused")
    public List<DeviceEntity> getAllPartnerDevices() {
        return getAllPartnerDevices(0);
    }

    public List<DeviceEntity> getAllDevices(int typeId) {
        return filterByType(deviceList, typeId);
    }

    public Long countByType(Integer type_id) {
        return deviceRepository.countByType_Id(type_id);
    }

    public Optional<DeviceEntity> getByDeviceId(Integer device_id) {
        return deviceRepository.findById(device_id);
    }

    public long getDaysAtCustomer(int deviceId) {
        EventEntity event = eventRepository.findFirstByDeviceIdAndActionIdOrderByIdDesc(deviceId, (long) 3);
        return calculateDuration(event);

    }

    private List<DeviceEntity> filterByType(List<DeviceEntity> devices, int typeId) {
        if (typeId == 0) {
            return devices;
        } else {
            return devices.stream().filter(device -> device.getType().getId() == typeId).toList();
        }
    }

    public void addDevice(ItemAddRequest deviceRequest, UserEntity user) {
        DeviceEntity device = new DeviceEntity();
        addItem(device, deviceRequest, user);


    }


    public void updateDevice(ItemEditRequest deviceRequest, UserEntity user) {
        Optional<DeviceEntity> deviceOptional = deviceRepository.findById(deviceRequest.getId());
        deviceOptional.ifPresent(device -> saveItem(device, deviceRequest, user));
    }

    public void moveDevice(MoveRequest moveRequest, UserEntity user) {
        Optional<DeviceEntity> deviceOptional = deviceRepository.findById(moveRequest.getId());
        deviceOptional.ifPresent(device -> moveDevice(device, moveRequest, user));
    }

    private void moveDevice(DeviceEntity device, MoveRequest request, UserEntity user) {
        Optional<LocationEntity> locationOptional = locationRepository.findById(request.getLocationTo());
        List<PartEntity> parts = partService.getAllDeviceParts(device.getId());
        locationOptional.ifPresent(location -> {
            EventEntity deviceEvent = eventService.moveEvent(device, user, location);
            device.setLocation(location);
            device.setUpdateDate(Instant.now());
            device.setUpdateUser(user);

            deviceRepository.save(device);
            eventRepository.save(deviceEvent);

            parts.forEach(part -> {
                EventEntity partEvent = eventService.moveEvent(part, user, location);
                part.setLocation(location);
                partRepository.save(part);
                eventRepository.save(partEvent);
            });
        });

    }

    public void addPart(int deviceId, int partId, UserEntity user) {
        partRepository.findById(partId).ifPresent(part -> deviceRepository.findById(deviceId).ifPresent(device -> {
            part.setDevice(device);
            partRepository.save(part);
            eventRepository.save(eventService.createEvent(part, user, "Компонент добавлен в устройство: " + device.getName()));
            eventRepository.save(eventService.createEvent(device, user, "Добавлен компонент: " + part.getName()));
        }));
    }

}
