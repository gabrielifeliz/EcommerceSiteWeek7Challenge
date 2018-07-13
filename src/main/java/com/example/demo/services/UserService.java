package com.example.demo.services;

import com.example.demo.models.AppRole;
import com.example.demo.models.AppUser;
import com.example.demo.repositories.AppRoleRepository;
import com.example.demo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    AppUserRepository userRepository;

    @Autowired
    AppRoleRepository roleRepository;

    @Autowired
    public UserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(AppUser user) {

        Set<AppRole> roles = new HashSet<>(Collections.singletonList(roleRepository.findByRole("USER")));
        user.setRoles(roles);
        userRepository.save(user);
    }
}