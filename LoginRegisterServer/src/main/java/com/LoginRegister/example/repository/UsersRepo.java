package com.LoginRegister.example.repository;

/*import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LoginRegister.example.entity.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users,String>{
	
	Optional<Users> findByEmail(String email);
	Users findByUsernameAndPassword(String username, String password);

    Users findByUsername(String username);

}*/
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LoginRegister.example.entity.Users;

public interface UsersRepo extends JpaRepository<Users,Long> {

	Optional<Users> findByEmail(String email);
    //Optional<Users> findByEmail(String email);

	//Optional<Users> findById(Users user_id);  
	//Optional<Users> findByUsername(String username);
	// Optional<Users> findByUser_id(Long user_id);
}
