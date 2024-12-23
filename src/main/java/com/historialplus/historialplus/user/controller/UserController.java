package com.historialplus.historialplus.user.controller;

import com.historialplus.historialplus.user.dto.request.DoctorCreationDto;
import com.historialplus.historialplus.user.dto.request.ManagementCreationDto;
import com.historialplus.historialplus.user.dto.request.UserUpdateDto;
import com.historialplus.historialplus.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.user.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<?> search(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String hospitalName,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) Integer stateId,
            Pageable pageable) {
        return service.searchUsers(username, dni, hospitalName, roleId, stateId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<UserResponseDto> user = service.findById(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/createManagementUser")
    public ResponseEntity<?> createManagementUser(@Valid @RequestBody ManagementCreationDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createManagementUser(userDto));
    }

    @PostMapping("/createDoctorUser")
    public ResponseEntity<?> createDoctorUser(@Valid @RequestBody DoctorCreationDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDoctorUser(userDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto userDto) {
        return ResponseEntity.ok(service.update(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}