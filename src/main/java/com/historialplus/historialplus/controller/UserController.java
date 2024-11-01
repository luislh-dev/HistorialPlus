package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.model.User;
import com.historialplus.historialplus.service.userservice.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> list(){
        return service.findAll();
    }

}