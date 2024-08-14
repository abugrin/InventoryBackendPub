package com.vinteo.inventory.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardModel {
    private Long storeDevicesCount;
    private Long customerDevicesCount;
    private Long partnerDevicesCount;
}
