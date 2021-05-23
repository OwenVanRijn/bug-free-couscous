package io.swagger.services;

import io.swagger.model.User;
import io.swagger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) { return userRepository.findUserByEmail(email); }

    public void deleteById(Integer id) { userRepository.deleteById(id); }
}
