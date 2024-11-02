package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.model.UserModel;
import com.historialplus.historialplus.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<UserModel> findById(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public UserModel save(UserModel userModel) {

        if (userModel.getPassword() != null) {
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        }

        return repository.save(userModel);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}