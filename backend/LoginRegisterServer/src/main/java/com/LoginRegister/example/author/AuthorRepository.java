package com.LoginRegister.example.author;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LoginRegister.example.entity.Users;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name); // Optional for safe null checks
   
    List<Author> findByUser(Users user);

	Optional<Users> findByNameAndUser(String authorName, Users user);

	
}



