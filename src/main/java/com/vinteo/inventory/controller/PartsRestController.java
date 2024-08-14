package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.PartEntity;
import com.vinteo.inventory.entity.UserEntity;
import com.vinteo.inventory.model.ItemAddRequest;
import com.vinteo.inventory.model.ItemEditRequest;
import com.vinteo.inventory.model.MoveRequest;
import com.vinteo.inventory.service.PartService;
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
public class PartsRestController extends EquipmentRestController {


    private PartService partService;
    private UserService userService;
    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/parts")
    public List<PartEntity> parts(@RequestParam(required = false) String typeId, @RequestParam(required = false) String store) {
        int typeIdValue = 0;
        try {
            typeIdValue = Integer.parseInt(typeId);
        } catch (NumberFormatException ignored) {}

        if (store != null) {
            switch (store) {
                case REQ_STORES -> {
                    return partService.getAllStoreParts(typeIdValue);
                }
                case REQ_CUSTOMERS -> {
                    return partService.getAllCustomerParts(typeIdValue);
                }
                case REQ_PARTNERS -> {
                    return partService.getAllPartnerParts(typeIdValue);
                }
                default -> {
                    return partService.getAllParts(typeIdValue);
                }
            }
        } else {
            return partService.getAllParts(typeIdValue);
        }
    }
    @GetMapping(value = "/api/parts/{device_id}")
    public List<PartEntity> deviceParts(@PathVariable int device_id){
        return partService.getAllDeviceParts(device_id);
    }

    @GetMapping(value = "/api/parts/available/{device_id}")
    public List<PartEntity> availableParts(@PathVariable int device_id){
        return partService.getAvailableParts(device_id);
    }

    @GetMapping(value = "/api/part/{part_id}")
    public Optional<PartEntity> device(@PathVariable int part_id) {
        return partService.getById(part_id);
    }

    @PostMapping(value = "/api/part/uninstall/{part_id}")
    public ResponseEntity<String> uninstallPart(@PathVariable int part_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());

        log.info("PART: " + part_id);
        partService.uninstallPart(part_id, user);
        return new ResponseEntity<>("Удалено", HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/part/move")
    public ResponseEntity<String> movePart(@RequestBody MoveRequest moveRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        partService.movePart(moveRequest, user);

        return new ResponseEntity<>("Компонент перемещен", HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/part/add")
    public ResponseEntity<String> addPart(@RequestBody ItemAddRequest partRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        partService.addPart(partRequest, user);
        return new ResponseEntity<>("Компонент добавлено", HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/part/edit")
    public ResponseEntity<String> editDevice(@RequestBody ItemEditRequest partRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());
        partService.updatePart(partRequest, user);

        return new ResponseEntity<>("Устройство изменено", HttpStatus.CREATED);
    }

}
