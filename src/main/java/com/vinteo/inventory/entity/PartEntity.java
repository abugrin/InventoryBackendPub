package com.vinteo.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "part", schema = "Inventory")
public class PartEntity implements ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pn", length = 100)
    private String pn;

    @Column(name = "serial", length = 50)
    private String serial;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "update_user", nullable = false)
    private UserEntity updateUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private EqStatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private DeviceTypeEntity type;

    @Transient
    private Long daysAtCustomer;

    @Transient
    private Long partsCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public UserEntity getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(UserEntity updateUser) {
        this.updateUser = updateUser;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public EqStatusEntity getStatus() {
        return status;
    }

    public void setStatus(EqStatusEntity status) {
        this.status = status;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public DeviceTypeEntity getType() { return type; }

    public void setType(DeviceTypeEntity type) { this.type = type; }

    public Long getDaysAtCustomer() {
        return daysAtCustomer;
    }

    public void setDaysAtCustomer(Long daysAtCustomer) {
        this.daysAtCustomer = daysAtCustomer;
    }

    public Long getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Long partsCount) {
        this.partsCount = partsCount;
    }
}