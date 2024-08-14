package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.DeviceTypeEntity;
import com.vinteo.inventory.entity.LocationEntity;
import com.vinteo.inventory.model.TestRequest;
import com.vinteo.inventory.model.TypeAddRequest;
import com.vinteo.inventory.model.TypeEditRequest;
import com.vinteo.inventory.service.DeviceTypeService;
import com.vinteo.inventory.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
public class TypesRestController {

    private DeviceTypeService deviceTypeService;

    @Autowired
    public void setDeviceTypeService(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @GetMapping(value = "/api/types")
    public List<DeviceTypeEntity> types() {
        return deviceTypeService.getAllTypes();

    }

    @PostMapping(value = "/api/type/add")
    public ResponseEntity<String> addType(@RequestBody TypeAddRequest req){
        //log.info("Form Posted: " + req.getName());
        deviceTypeService.addType(req.getName());

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/edit_type")
    public ResponseEntity<String> editType(@RequestBody TypeEditRequest req){
        log.info("Form Posted: " + req.getName() + " " + req.getId());
        deviceTypeService.editType(req);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
