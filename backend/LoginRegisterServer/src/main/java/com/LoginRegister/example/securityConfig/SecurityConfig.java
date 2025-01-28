package com.LoginRegister.example.securityConfig;

/*import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable(); // Disable CSRF for development and allow CORS
        return http.build();
    }
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*http.cors().and().csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
            .anyRequest().authenticated();

        return http.build();*/
		http.csrf().disable()  // Disable CSRF if necessary
        .authorizeRequests()
        //.requestMatchers("/category/add").permitAll()
        .requestMatchers("/api/auth/addbooks").permitAll() 
        .requestMatchers("/api/auth/**").permitAll()  // Correct way to permit authentication routes
        .anyRequest().authenticated()  // Any other request requires authentication
        .and()
        
        .cors();  // Enable CORS support
    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Set up password encoding for authentication
    }
}
