package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserUpdateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.service.userservice.IUserService;
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

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserCreateDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDto));
    }

    @PostMapping("/createHospitalUserByManagement")
    public ResponseEntity<?> createHospitalUserByManagement(@Valid @RequestBody UserCreateDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createHospitalUserByManagement(userDto));
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