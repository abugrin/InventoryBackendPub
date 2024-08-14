package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.ContactEntity;
import com.vinteo.inventory.model.ContactAddRequest;
import com.vinteo.inventory.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ContactsRestController extends EquipmentRestController {
    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "/api/contacts/location/{location}")
    public List<ContactEntity> locations(@PathVariable int location) {
        return contactService.getContactsByLocationId(location);
    }

    @PostMapping(value = "/api/contacts/add")
    public ResponseEntity<String> addContact(@RequestBody ContactAddRequest request){
        log.info("Contact add request: " + request.getName());
        contactService.addContact(request);
        return new ResponseEntity<>("Контакт добавлен", HttpStatus.CREATED);
    }

}
