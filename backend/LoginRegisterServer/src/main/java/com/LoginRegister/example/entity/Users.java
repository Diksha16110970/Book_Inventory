package com.LoginRegister.example.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id",nullable = false, unique = true)
	private Long userId;
	
	
	@Column(nullable = false, unique = true)
	private String email;
	 @Column(nullable = false)
	private String name;
	 @Column(nullable = false)
	private String password;
	 
	 @CreationTimestamp
	 @Column(name = "created_at", updatable = false)
	 private LocalDateTime createdAt;

	 @UpdateTimestamp
	 @Column(name = "updated_at")
	 private LocalDateTime updatedAt;
	
	public Users(){
		
	}
	public Users(String email, String name, String password) {
		super();
		
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getCreatedAt() 
	{
	       return createdAt;
	}
	public LocalDateTime getUpdatedAt() 
	{
	       return updatedAt;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
