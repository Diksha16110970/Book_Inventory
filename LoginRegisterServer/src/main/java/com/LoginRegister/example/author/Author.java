//package com.LoginRegister.example.author;
////
////import java.time.LocalDateTime;
////
////import org.hibernate.annotations.CreationTimestamp;
////import org.hibernate.annotations.UpdateTimestamp;
////
////import com.LoginRegister.example.entity.Users;
////
////import jakarta.persistence.Column;
////import jakarta.persistence.Entity;
////import jakarta.persistence.FetchType;
////import jakarta.persistence.GeneratedValue;
////import jakarta.persistence.GenerationType;
////import jakarta.persistence.Id;
////import jakarta.persistence.JoinColumn;
////import jakarta.persistence.ManyToOne;
////
////@Entity
////public class Author {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "author_id", nullable = false, unique = true)
////    private Long authorId;
////
////    @Column(nullable = false)
////    private String name;
////
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id", nullable = false)
////    private Users user;
////
////    @CreationTimestamp
////    @Column(name = "created_at", updatable = false)
////    private LocalDateTime createdAt;
////
////    @UpdateTimestamp
////    @Column(name = "updated_at")
////    private LocalDateTime updatedAt;
////
////    // Getters and setters
////    public Long getAuthorId() {
////        return authorId;
////    }
////
////    public void setAuthorId(Long authorId) {
////        this.authorId = authorId;
////    }
////
////    public String getName() {
////        return name;
////    }
////
////    public void setName(String name) {
////        this.name = name;
////    }
////
////    public Users getUser() {
////        return user;
////    }
////
////    public void setUser(Users user) {
////        this.user = user;
////    }
////
////    public LocalDateTime getCreatedAt() {
////        return createdAt;
////    }
////
////    public LocalDateTime getUpdatedAt() {
////        return updatedAt;
////    }
////}
//package com.LoginRegister.example.author;
//
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import com.LoginRegister.example.entity.Users;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Author {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "author_id", nullable = false, unique = true)
//    private Long authorId;
//    @Column(nullable = false)
//    private String name;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at",nullable = false)
//    private LocalDateTime updatedAt;
//
//    // Getters and setters
//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(Long authorId) {
//        this.authorId = authorId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//	public void setCreatedAt(LocalDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public void setUpdatedAt(LocalDateTime updatedAt) {
//		this.updatedAt = updatedAt;
//	}
//    
//}


//package com.LoginRegister.example.author;
//
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import com.LoginRegister.example.entity.Users;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Author {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "author_id", nullable = false, unique = true)
//    private Long authorId;
//
//    @Column(nullable = false)
//    private String name;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    // Getters and setters
//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(Long authorId) {
//        this.authorId = authorId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Users getUser() {
//        return user;
//    }package com.LoginRegister.example.author;

//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import com.LoginRegister.example.entity.Users;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Author {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "author_id", nullable = false, unique = true) // Correct column name
//    private Long authorId;
//
//    @Column(nullable = false)
//    private String name;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt;
//
//    // Getters and setters
//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(Long authorId) {
//        this.authorId = authorId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}

//
//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//}



//package com.LoginRegister.example.author;
//
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import com.LoginRegister.example.entity.Users;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Author {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "author_id", nullable = false, unique = true) // Correct column name
//    private Long authorId;
//
//    @Column(nullable = false)
//    private String name;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    //@JoinColumn(name = "user_id", nullable = false)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false) // Updated
//    private Users user;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt;
//
//    // Getters and setters
//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(Long authorId) {
//        this.authorId = authorId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}

package com.LoginRegister.example.author;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.LoginRegister.example.entity.Users;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false, unique = true)
    private Long authorId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false) // Updated
    private Users user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Getters and setters
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

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
}


