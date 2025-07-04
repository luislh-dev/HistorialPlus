package com.historialplus.historialplus.internal.user.controller;

import com.historialplus.historialplus.internal.user.dto.request.DoctorCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.ManagementCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.UserUpdateDto;
import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<UserListResponseDto> search(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String hospitalName,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) Integer stateId,
            Pageable pageable) {
        return service.searchUsers(username, dni, hospitalName, roleId, stateId, pageable);
    }

    @PostMapping("createManagementUser")
    public ResponseEntity<UserResponseDto> createManagementUser(@Valid @RequestBody ManagementCreationDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createManagementUser(userDto));
    }

    @PostMapping("createDoctorUser")
    public ResponseEntity<UserResponseDto> createDoctorUser(@Valid @RequestBody DoctorCreationDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDoctorUser(userDto));
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto userDto) {
        return ResponseEntity.ok(service.update(id, userDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}