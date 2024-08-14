package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.LocationEntity;
import com.vinteo.inventory.entity.UserEntity;
import com.vinteo.inventory.model.ItemAddRequest;
import com.vinteo.inventory.model.LocationAddRequest;
import com.vinteo.inventory.service.LocationService;
import com.vinteo.inventory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class LocationsRestController extends EquipmentRestController {
    private LocationService locationService;
    private UserService userService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/locations")
    public List<LocationEntity> locations() {

        return locationService.getAllLocations();

    }

    @GetMapping(value = "/api/locations/{type}")
    public List<LocationEntity> locations(@PathVariable String type) {
        switch (type) {
            case REQ_STORES -> {
                return locationService.getLocationsByTypeId(0);
            }
            case REQ_CUSTOMERS -> {
                return locationService.getLocationsByTypeId(1);
            }
            case REQ_PARTNERS -> {
                return locationService.getLocationsByTypeId(2);
            }
            default -> {
                return locationService.getAllLocations();
            }
        }
    }

    @PostMapping(value = "/api/location/add")
    public ResponseEntity<String> addLocation(@RequestBody LocationAddRequest request){
        log.info("Location add request: " + request.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUser(authentication.getName());

        locationService.addLocation(request, user);
        return new ResponseEntity<>("Место добавлено", HttpStatus.CREATED);
    }
}
