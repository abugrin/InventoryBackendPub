package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.LocationEntity;
import com.vinteo.inventory.entity.UserEntity;
import com.vinteo.inventory.model.LocationAddRequest;
import com.vinteo.inventory.repository.DeviceRepository;
import com.vinteo.inventory.repository.LocationRepository;
import com.vinteo.inventory.repository.LocationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class LocationService {

    private LocationRepository locationRepository;
    private DeviceRepository deviceRepository;
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    @Autowired
    public void setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    @Autowired
    public void setLocationTypeRepository(LocationTypeRepository locationTypeRepository) {
        this.locationTypeRepository = locationTypeRepository;
    }

    public List<LocationEntity> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<LocationEntity> getLocationById(Integer location_id) {
        return locationRepository.findById(location_id);
    }

    public List<LocationEntity> getLocationsByTypeId(Integer type_id) {
        List<LocationEntity> locations = locationRepository.findByTypeId(type_id);
        for (LocationEntity location : locations) {
            location.setDeviceCount(deviceRepository.countByLocationId(location.getId()));
        }
        return locations;
    }

    public void addLocation(LocationAddRequest request, UserEntity user) {
        LocationEntity location = new LocationEntity();
        location.setName(request.getName());
        location.setAddress(request.getAddress());
        location.setUpdateUser(user);
        location.setCreateDate(Instant.now());
        location.setUpdateDate(Instant.now());
        locationTypeRepository.findById(request.getType()).ifPresent(location::setType);

        locationRepository.save(location);
    }
}
