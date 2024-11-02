package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.model.StateModel;
import com.historialplus.historialplus.model.UserModel;
import com.historialplus.historialplus.service.stateservice.IStateService;
import com.historialplus.historialplus.service.userservice.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService service;
    private final IStateService stateService;

    public UserController(IUserService service, IStateService stateService) {
        this.service = service;
        this.stateService = stateService;
    }

    @GetMapping
    public List<UserModel> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<UserModel> user = service.findById(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        if (userModel.getStateModel() == null || userModel.getStateModel().getId() == null) {
            return ResponseEntity.badRequest().body("El estado o su ID no puede ser nulo");
        }

        Optional<StateModel> stateOptional = stateService.findById(userModel.getStateModel().getId());
        if (stateOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("ID inválido para el estado");
        }
        userModel.setStateModel(stateOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userModel));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}