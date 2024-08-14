package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.EventEntity;
import com.vinteo.inventory.model.DashboardModel;
import com.vinteo.inventory.service.DashboardService;
import com.vinteo.inventory.service.DeviceService;
import com.vinteo.inventory.service.EventService;
import com.vinteo.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DashboardRestController {
    private DashboardService dashboardService;

    @Autowired
    public void setDashboardService(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard")
    public DashboardModel dashboard(){
        return dashboardService.getDashboardData();
    }

    @GetMapping("/api/dashboard/events")
    public List<EventEntity> events(){
        return dashboardService.getLastMoveEvents();
    }

}
