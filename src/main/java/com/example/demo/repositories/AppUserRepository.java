package com.example.demo.repositories;

import com.example.demo.models.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String s);
    Long countByUsername(String s);
    // findAllByTopTen
}