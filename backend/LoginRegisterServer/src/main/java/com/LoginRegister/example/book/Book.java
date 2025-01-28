//package com.LoginRegister.example.book;
//
//
//
//import jakarta.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import com.LoginRegister.example.author.Author;
//import com.LoginRegister.example.category.Category;
//import com.LoginRegister.example.entity.Users;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
////@Entity
//@Table(name = "book", uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"title", "author_id", "category_id"}) // Enforcing uniqueness for title, author_id, and genre_id
//})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Book {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long bookId;
//
//    @Column(nullable = false, length = 100)
//    private String title;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to Author
//    @JoinColumn(name = "author_id", referencedColumnName = "authorId", foreignKey = @ForeignKey(name = "fk_author_constraint"))
//    @JsonIgnore
//    private Author author; // Author entity reference
//
//    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to Genre
//    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", foreignKey = @ForeignKey(name = "fk_genre_constraint"))
//    @JsonIgnore
//    private Category genre; // Genre entity reference
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @Column(nullable = false, precision = 8, scale = 2)
//    private BigDecimal price;
//
//    @Column(nullable = false, length = 13)
//    private String isbn;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to User
//    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_constraints"))
//    @JsonIgnore
//    private Users user; // User entity reference
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    // Getters and Setters
//    public Long getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Long bookId) {
//        this.bookId = bookId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }
//
//    public Category getGenre() {
//        return genre;
//    }
//
//    public void setGenre(Category genre) {
//        this.genre = genre;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
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



//package com.LoginRegister.example.book;
//
//import jakarta.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import com.LoginRegister.example.author.Author;
//import com.LoginRegister.example.category.Category;
//import com.LoginRegister.example.entity.Users;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
//@Table(name = "book", uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"title", "author_id", "category_id"})
//})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Book {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long bookId;
//
//    @Column(nullable = false, length = 100)
//    private String title;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to Author
//    @JoinColumn(name = "author_id", referencedColumnName = "author_id", foreignKey = @ForeignKey(name = "fk_author_constraint"))
//    @JsonIgnore
//    private Author author;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to Category
//    @JoinColumn(name = "category_id", referencedColumnName = "category_id", foreignKey = @ForeignKey(name = "fk_category_constraint"))
//    @JsonIgnore
//    private Category category;
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @Column(nullable = false, precision = 8, scale = 2)
//    private BigDecimal price;
//
//    @Column(nullable = false, length = 13)
//    private String isbn;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to User
//    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_constraints"))
//    @JsonIgnore
//    private Users user;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    // Getters and Setters
//    public Long getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Long bookId) {
//        this.bookId = bookId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
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

package com.LoginRegister.example.book;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.LoginRegister.example.author.Author;
import com.LoginRegister.example.category.Category;
import com.LoginRegister.example.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "author_id", foreignKey = @ForeignKey(name = "fk_author_constraint" ),nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_user_constraint" ),nullable = false) // Updated
    private Users user;
    
    @ManyToOne(fetch = FetchType.LAZY) // Foreign key relationship to Category
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", foreignKey = @ForeignKey(name = "fk_category_constraint"))
    //@JsonIgnore
    private Category category;
    
     

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, length = 13)
    private String isbn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
    
}



