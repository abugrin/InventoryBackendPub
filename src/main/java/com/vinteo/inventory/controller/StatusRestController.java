package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.DeviceTypeEntity;
import com.vinteo.inventory.entity.EqStatusEntity;
import com.vinteo.inventory.model.TypeAddRequest;
import com.vinteo.inventory.model.TypeEditRequest;
import com.vinteo.inventory.service.DeviceTypeService;
import com.vinteo.inventory.service.EqStatusService;
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
public class StatusRestController {

    private EqStatusService statusService;

    @Autowired
    public void setStatusService(EqStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping(value = "/api/statuses")
    public List<EqStatusEntity> statuses() {
        return statusService.getAllStatuses();

    }


}
