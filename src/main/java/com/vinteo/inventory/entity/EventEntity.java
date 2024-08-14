package com.vinteo.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "event")
public class EventEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "time", nullable = false)
    private Instant time;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "action", nullable = false)
    private EventActionEntity action;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "object", nullable = false)
    private EventObjectEntity object;

    @Getter
    @Column(name = "description")
    private String description;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device")
    private DeviceEntity device;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part")
    private PartEntity part;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_from")
    private LocationEntity locationFrom;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_to")
    private LocationEntity locationTo;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user", nullable = false)
    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setLocationTo(LocationEntity locationTo) {
        this.locationTo = locationTo;
    }

    public void setLocationFrom(LocationEntity locationFrom) {
        this.locationFrom = locationFrom;
    }

    public void setPart(PartEntity part) {
        this.part = part;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObject(EventObjectEntity object) {
        this.object = object;
    }

    public void setAction(EventActionEntity action) {
        this.action = action;
    }

    public String getTime() {
        Date date = Date.from(time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public Instant getDate() {
        return time;
    }


    public void setTime(Instant time) {
        this.time = time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}