package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.DeviceEntity;
import com.vinteo.inventory.entity.UserEntity;
import com.vinteo.inventory.model.ItemAddRequest;
import com.vinteo.inventory.model.ItemEditRequest;
import com.vinteo.inventory.model.MoveRequest;
import com.vinteo.inventory.service.DeviceService;
import com.vinteo.inventory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
public class DevicesRestController extends EquipmentRestController{


    private DeviceService deviceService;
    private UserService userService;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/devices")
    public List<DeviceEntity> devices(@RequestParam(required = false) String typeId, @RequestParam(required = false) String store) {
        int typeIdValue = 0;
        try {
            typeIdValue = Integer.parseInt(typeId);
        } catch (NumberFormatException ignored) {}

        //response.put("authenticatedUser", userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (store != null) {
        switch (store) {
            case REQ_STORES -> { return deviceService.getAllStoreDevices(typeIdValue); }
            case REQ_CUSTOMERS -> { return deviceService.getAllCustomerDevices(typeIdValue); }
            case REQ_PARTNERS -> { return deviceService.getAllPartnerDevices(typeIdValue); }
            default -> { return deviceService.getAllDevices(typeIdValue); }
        }} else {
            return deviceService.getAllDevices(typeIdValue);
        }
    }
    @GetMapping(value = "/api/device/{device_id}")
    public Optional<DeviceEntity> device(@PathVariable int device_id) {
        return deviceService.getByDeviceId(device_id);
    }

    @PostMapping(value = "/api/device/{device_id}/addpart/{part_id}")
    public ResponseEntity<String> addPart(@PathVariable int device_id, @PathVariable int part_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        log.info("Device ID: " + device_id + " add part ID: " + part_id);
        deviceService.addPart(device_id, part_id, user);
        return new ResponseEntity<>("Компонент добавлен", HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/device/add")
    public ResponseEntity<String> addDevice(@RequestBody ItemAddRequest deviceRequest){
        //log.info("Location request: " + deviceRequest.getLocationId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        deviceService.addDevice(deviceRequest, user);
        return new ResponseEntity<>("Устройство добавлено", HttpStatus.CREATED);
    }
    @PostMapping(value = "/api/device/edit")
    public ResponseEntity<String> editDevice(@RequestBody ItemEditRequest deviceRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        deviceService.updateDevice(deviceRequest, user);

        return new ResponseEntity<>("Устройство изменено", HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/device/move")
    public ResponseEntity<String> moveDevice(@RequestBody MoveRequest moveRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        deviceService.moveDevice(moveRequest, user);

        return new ResponseEntity<>("Устройство перемещено", HttpStatus.CREATED);
    }

}
