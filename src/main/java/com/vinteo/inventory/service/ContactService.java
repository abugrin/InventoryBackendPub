package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.ContactEntity;
import com.vinteo.inventory.model.ContactAddRequest;
import com.vinteo.inventory.repository.ContactRepository;
import com.vinteo.inventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service
@RequestScope
public class ContactService {
    private ContactRepository contactRepository;
    private LocationRepository locationRepository;
    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<ContactEntity> getContactsByLocationId(Integer location_id) {
        return contactRepository.findAllByLocationId(location_id);
    }

    public void addContact(ContactAddRequest request) {
        ContactEntity contact = new ContactEntity();
        contact.setName(request.getName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        locationRepository.findById(request.getLocation()).ifPresent(contact::setLocation);
        contactRepository.save(contact);
    }
}
