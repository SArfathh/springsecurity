package org.arfath.security.service;

import org.arfath.security.model.UserEntity;
import org.arfath.security.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity register(UserEntity user) {
        String hasedpassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(hasedpassword);
        return userRepository.save(user);
    }
}
