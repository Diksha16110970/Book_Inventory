package com.LoginRegister.example.service;

//import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;

//import com.LoginRegister.example.entity.Users;
//import com.LoginRegister.example.repository.UsersRepo;
//import com.LoginRegister.example.requests.LoginRequest;

//@Service
//public class UserService {
	
	//@Autowired
	//UsersRepo usersRepo;
	
//	@Autowired
//    private BCryptPasswordEncoder passwordEncoder;
	//private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	public Users addUser(Users users)
	//{
		//users.setPassword(passwordEncoder.encode(users.getPassword()));
		//return usersRepo.save(users);
	//}
	//public Boolean loginUsers(LoginRequest loginRequest) {
		
		//Optional<Users> user = usersRepo.findById(loginRequest.getEmail());
		
		//if (user==null) { 
          //  return false;
        //}
		//Users user1 = user.get();
		//Users foundUser = user.get();
        // Verify the entered password against the stored encrypted password
		//if(passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
			//return true;
			
	//	}
		//else {
			//return false;
		//}
		
		/*if(!user1.getPassword().equals(loginRequest.getPassword()))
		{
			return false;
		}*/
		
		//return true;
	//}

//}

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.repository.UsersRepo;
import com.LoginRegister.example.response.LoginResponse;
import com.LoginRegister.example.security.JwtUtil;
import com.LoginRegister.example.userdto.UserDTO;


@Service
public class UserService {

    private final UsersRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    

    public UserService(UsersRepo userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Users registerUser(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        Users user = new Users();
        //user.setUser_id(userDTO.getUser_id());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }
    
//    public Optional<Users> findByUserId(Long user_id) {
//        return userRepository.findById(user_id);
//    }
    /*public Users authenticate(String email, String password) {
        Optional<Users> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Email not found");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        return user.get();*/
    
    public LoginResponse authenticate(String email, String password) {
        Optional<Users> user = userRepository.findByEmail(email);
        Users user1 = user.get();
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Email not found");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = jwtUtil.generateToken(user1.getUserId());
        return new LoginResponse(token);
    }

    
    
    public Optional<Users> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    
    
        // Return the user if authentication succeeds
    }
    
    

    
    

