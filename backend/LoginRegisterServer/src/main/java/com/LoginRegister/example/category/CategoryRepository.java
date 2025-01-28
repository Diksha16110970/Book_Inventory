package com.LoginRegister.example.category;


import org.springframework.data.jpa.repository.JpaRepository;

import com.LoginRegister.example.entity.Users;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name); // Optional for safe null checks
        List<Category> findByUser(Users user);
    }

   
