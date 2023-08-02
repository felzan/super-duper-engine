package com.felzan.ecommerce.service;

import com.felzan.ecommerce.domain.User;
import com.felzan.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public User getUserById(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }
}
