package com.vinteo.inventory.entity;

import java.time.Instant;

public interface ItemEntity {
    Integer getId();
    void setName(String s);

    void setSerial(String s);

    void setPn(String s);

    void setDescription(String s);

    void setType(DeviceTypeEntity type);

    void setUpdateDate(Instant time);

    void setCreateDate(Instant time);

    void setUpdateUser(UserEntity updateUser);

    void setStatus(EqStatusEntity status);

    void setLocation(LocationEntity location);

    String getName();

    String getSerial();

    String getPn();

    EqStatusEntity getStatus();

    String getDescription();

    DeviceTypeEntity getType();

    LocationEntity getLocation();
}
