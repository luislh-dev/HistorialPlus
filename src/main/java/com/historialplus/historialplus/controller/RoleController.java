package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.service.roleservice.IRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

}
