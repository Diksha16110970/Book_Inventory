package com.LoginRegister.example.controller;

/*import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.requests.LoginRequest;
import com.LoginRegister.example.service.UserService;

@RestController
@CrossOrigin(origins ="http://localhost:3000")
public class UsersController {

		@Autowired
		UserService userService;
		
		@PostMapping("/addUser")
		//@CrossOrigin(origins ="http://localhost:3000")
		public Users addUser(@RequestBody Users user) {
			 if (user.getEmail() == null || user.getName() == null || user.getPassword() == null) {
			        throw new IllegalArgumentException("All fields are required!");
			    }
			 
			 
			
			return  userService.addUser(user);
		}
		
		
		@PostMapping("/loginUser")
		//@CrossOrigin(origins ="http://localhost:3000")
		public Boolean loginUser(@RequestBody LoginRequest loginRequest)
		{
			return userService.loginUsers(loginRequest);
			
		}
}*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.requests.LoginRequest;
import com.LoginRegister.example.response.LoginResponse;
import com.LoginRegister.example.security.JwtUtil;
import com.LoginRegister.example.service.UserService;
import com.LoginRegister.example.userdto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class UsersController {

    @Autowired
    private UserService userService; 
    
    @Autowired
    private JwtUtil jwtUtil;
    
  /* @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO) {
        try {
            Users user = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "status", "success",
                "user", user 
                // Include relevant user details
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "message", e.getMessage(),
                "status", "error"
            ));
        }

    } */
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO) {
        try {
            LoginResponse loginResponse = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "status", "success",
                "token", loginResponse.getToken()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "message", e.getMessage(),
                "status", "error"
            ));
        }
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            Users user = userService.registerUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            // Return a well-formed JSON error response
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    
    
    @GetMapping("/userName/{userId}")
    public ResponseEntity<String> getUserName(@PathVariable Long userId) {
        Optional<Users> userOpt = userService.findByUserId(userId);
        if (userOpt.isPresent()) {
            String userName = userOpt.get().getName();
            return ResponseEntity.ok(userName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    
    

    // ErrorResponse DTO
    public class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        
        
        
        
}
}