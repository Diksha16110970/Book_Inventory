package com.LoginRegister.example.service;

import java.util.HashMap;
import java.util.Map;

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
import java.util.Random;
//import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.repository.UsersRepo;
import com.LoginRegister.example.response.LoginResponse;
import com.LoginRegister.example.security.JwtUtil;
import com.LoginRegister.example.userdto.UserDTO;

import jakarta.mail.internet.MimeMessage;


@Service
public class UserService {

    private final UsersRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JavaMailSender mailSender;
    
    //private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, Long> otpExpiry = new HashMap<>();

    public UserService(UsersRepo userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.mailSender = mailSender;
    }

    public Users registerUser(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        Optional<Users> user1 = userRepository.findByEmail(userDTO.getEmail());
        if(user1.isPresent()) {
        	 throw new IllegalArgumentException("Email already Exist!");
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

    
    public void sendResetToken(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Email not found!");
        }

        String otp = generateOTP();
        otpStorage.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + 5 * 60 * 1000); // Expiry in 5 minutes

        sendOtpEmail(email, otp); // Send OTP via email
    }

    // ✅ Reset Password if OTP is valid
    public void resetPassword(String email, String otp, String newPassword) {
        String storedOtp = otpStorage.get(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP!");
        }

        if (System.currentTimeMillis() > otpExpiry.get(email)) {
            throw new IllegalArgumentException("OTP has expired!");
        }

        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        Users user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpStorage.remove(email);
        otpExpiry.remove(email);
    }

    // ✅ Generate a 6-digit OTP
    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    // ✅ Send OTP Email
    private void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Password Reset OTP");
            helper.setText("Your OTP for password reset is: " + otp + "\nThis OTP will expire in 5 minutes.");

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
}   
    

    
    

