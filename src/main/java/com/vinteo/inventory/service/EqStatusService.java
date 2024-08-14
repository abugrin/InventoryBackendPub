package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.EqStatusEntity;
import com.vinteo.inventory.repository.EqStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EqStatusService {
    EqStatusRepository statusRepository;

    @Autowired
    public void setStatusRepository(EqStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<EqStatusEntity> getAllStatuses(){
        return statusRepository.findAll();
    }
}
