package com.vinteo.inventory.controller;

import com.vinteo.inventory.entity.UserEntity;
import com.vinteo.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestController {
    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public UserEntity user(@RequestParam String username){
        UserEntity user = userService.getUser(username);
        user.setPicture(null);
        return user;
    }
}
