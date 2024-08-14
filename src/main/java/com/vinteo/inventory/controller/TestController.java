package com.vinteo.inventory.controller;

import com.vinteo.inventory.model.TestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class TestController {

    @PostMapping(value = "/api/formtest")
    public ResponseEntity<String> submit(@RequestBody String req){
        log.info("Form Posted: " + req);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

}
