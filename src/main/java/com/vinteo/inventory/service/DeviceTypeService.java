package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.DeviceTypeEntity;
import com.vinteo.inventory.model.TypeEditRequest;
import com.vinteo.inventory.repository.DeviceTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class DeviceTypeService {

    private DeviceService deviceService;
    private PartService partService;
    private DeviceTypeRepository typeRepository;
    private List<DeviceTypeEntity> typeList;
    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @Autowired
    public void setTypeRepository(DeviceTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    @PostConstruct
    void init() {
        typeList = typeRepository.findAll();
        for (DeviceTypeEntity typeEntity : typeList) {
            typeEntity.setDeviceCount(deviceService.countByType(typeEntity.getId()));
            typeEntity.setPartCount(partService.countByType(typeEntity.getId()));
        }
    }

    public List<DeviceTypeEntity> getAllTypes() {
        return typeList;
    }

    public boolean addType(String name) {
        DeviceTypeEntity type = new DeviceTypeEntity();
        type.setName(name);
        try {
            typeRepository.save(type);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editType(TypeEditRequest request) {
        Optional<DeviceTypeEntity> typeOptional = typeRepository.findById(request.getId());
        typeOptional.ifPresent(type -> {
            type.setName(request.getName());
            typeRepository.save(type);
        });
        return true;
    }


}
