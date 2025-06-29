package org.arfath.security.service;

import org.arfath.security.model.UserEntity;
import org.arfath.security.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }



    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }


}
