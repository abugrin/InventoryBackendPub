package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.EventEntity;
import com.vinteo.inventory.entity.LocationEntity;
import com.vinteo.inventory.model.DashboardModel;
import com.vinteo.inventory.repository.DeviceRepository;
import com.vinteo.inventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    public static int LOCATION_STORE = 0;
    public static int LOCATION_CUSTOMER = 1;
    public static int LOCATION_PARTNER = 2;

    EventService eventService;
    DeviceRepository deviceRepository;
    LocationRepository locationRepository;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public DashboardModel getDashboardData(){
        DashboardModel dashboard = new DashboardModel();
        List<LocationEntity> stores = locationRepository.findByTypeId(LOCATION_STORE);
        List<LocationEntity> customers = locationRepository.findByTypeId(LOCATION_CUSTOMER);
        List<LocationEntity> partners = locationRepository.findByTypeId(LOCATION_PARTNER);


        dashboard.setStoreDevicesCount(sumDeviceCount(stores));
        dashboard.setCustomerDevicesCount(sumDeviceCount(customers));
        dashboard.setPartnerDevicesCount(sumDeviceCount(partners));
        return dashboard;
    }

    public List<EventEntity> getLastMoveEvents(){
        return eventService.getLastMoveEvents();
    }

    private Long sumDeviceCount(List<LocationEntity> locations) {
        long devicesCount = 0L;
        for (LocationEntity location : locations) {
            devicesCount = devicesCount + deviceRepository.countByLocationId(location.getId());
        }

        return devicesCount;
    }
}
