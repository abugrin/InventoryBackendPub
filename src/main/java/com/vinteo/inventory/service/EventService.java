package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.*;
import com.vinteo.inventory.model.EventModel;
import com.vinteo.inventory.repository.EventActionRepository;
import com.vinteo.inventory.repository.EventObjectRepository;
import com.vinteo.inventory.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.util.List;

@Service
@RequestScope
public class EventService {
    private static final long ACTION_MOVE = 3;
    private EventRepository eventRepository;
    private EventActionRepository actionRepository;
    private EventObjectRepository objectRepository;


    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setActionRepository(EventActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Autowired
    public void setObjectRepository(EventObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }


    public List<EventEntity> getLastEvents() {
        List<EventEntity> events = eventRepository.findFirst5ByOrderByIdDesc();
        for (EventEntity event : events) {
            event.getUser().setPicture(null);
        }
        return events;
    }

    public List<EventEntity> getLastMoveEvents() {
        List<EventEntity> events = eventRepository.findFirst5ByAction_IdOrderByIdDesc(ACTION_MOVE);
        clearUserPictures(events);
        return events;
    }

    public List<EventEntity> getByDeviceId(int deviceId) {
        List<EventEntity> events = eventRepository.findByDeviceIdOrderByIdDesc(deviceId);
        clearUserPictures(events);
        return events;
    }

    public List<EventEntity> getByPartId(int partId) {
        List<EventEntity> events = eventRepository.findByPartIdOrderByIdDesc(partId);
        clearUserPictures(events);
        return events;
    }

    public EventEntity createEvent(Object entity, UserEntity user, String changes) {
        EventEntity event = new EventEntity();
        actionRepository.findById(EventModel.ACTION_CHANGE).ifPresent(event::setAction);
        SetEventObject(entity, event);

        event.setDescription(EventModel.EVENT_CHANGE + "\n" + changes);

        event.setTime(Instant.now());
        event.setUser(user);

        return event;
    }

    public EventEntity addEvent(ItemEntity entity, UserEntity user){
        EventEntity event = new EventEntity();
        actionRepository.findById(EventModel.ACTION_ADD).ifPresent(event::setAction);

        SetEventObject(entity, event);

        event.setDescription(EventModel.EVENT_ADD);
        event.setLocationFrom(entity.getLocation());
        event.setTime(Instant.now());
        event.setUser(user);

        return event;
    }

    public EventEntity moveEvent(ItemEntity entity, UserEntity user, LocationEntity location){
        EventEntity event = new EventEntity();
        actionRepository.findById(EventModel.ACTION_MOVE).ifPresent(event::setAction);

        SetEventObject(entity, event);

        event.setDescription(EventModel.EVENT_MOVE);
        event.setLocationFrom(entity.getLocation());
        event.setLocationTo(location);
        event.setTime(Instant.now());
        event.setUser(user);

        return event;
    }

    private void clearUserPictures(List<EventEntity> events) {
        events.forEach(event -> event.getUser().setPicture(null));
    }

    private void SetEventObject(Object entity, EventEntity event) {
        if (entity instanceof DeviceEntity) {
            objectRepository.findById(EventModel.OBJECT_DEVICE).ifPresent(event::setObject);
            event.setDevice((DeviceEntity) entity);
        } else if (entity instanceof PartEntity){
            objectRepository.findById(EventModel.OBJECT_PART).ifPresent(event::setObject);
            event.setPart((PartEntity) entity);
        } else {
            objectRepository.findById(EventModel.OBJECT_GENERIC).ifPresent(event::setObject);
        }
    }



}
