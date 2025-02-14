package com.LoginRegister.example.author;



import java.time.LocalDateTime;

import com.LoginRegister.example.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;

public class AuthorDTO {
	
	private Long authorId;
	
	private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	@NotEmpty(message = "Name is required")
    
    private String name;

	//@JsonIgnore
    private Long user_id; // Holds user ID for linking
	 private Users user;
	 
	 public Users getUser() {
	        return user;
	    }

	    public void setUser(Users user) {
	        this.user = user;
	    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
