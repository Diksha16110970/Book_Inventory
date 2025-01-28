//package com.LoginRegister.example.book;
//
//import jakarta.validation.constraints.NotNull;
//import java.math.BigDecimal;
//
//import com.LoginRegister.example.author.AuthorDTO;
//import com.LoginRegister.example.category.CategoryDTO;
//import com.LoginRegister.example.userdto.UserDTO;
//
//public class BookDTO {
//
//    @NotNull(message = "Title is required")
//    private String title;
//    
//    private Long bookId;
//
//    public Long getBookId() {
//		return bookId;
//	}
//
//	public void setBookId(Long bookId) {
//		this.bookId = bookId;
//	}
//
//	private Long userId;
//    
//    private AuthorDTO author;
//    private CategoryDTO genre;
//    
//    private Long authorId;
//
//    private Long genreId;
//
//    @NotNull(message = "Quantity is required")
//    private Integer quantity;
//
//    @NotNull(message = "Price is required")
//    private BigDecimal price;
//
//    @NotNull(message = "ISBN is required")
//    private String isbn;
//
//    private UserDTO user;
//
//    // Getters and Setters
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public AuthorDTO getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(AuthorDTO author) {
//        this.author = author;
//    }
//
//    public CategoryDTO getGenre() {
//        return genre;
//    }
//
//    public void setGenre(CategoryDTO genre) {
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
//    public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public BigDecimal getPrice() {
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
//    public UserDTO getUser() {
//        return user;
//    }
//
//    public void setUser(UserDTO user) {
//        this.user = user;
//    }
//
//	public Long getAuthorId() {
//		return authorId;
//	}
//
//	public void setAuthorId(Long authorId) {
//		this.authorId = authorId;
//	}
//
//	public Long getGenreId() {
//		return genreId;
//	}
//
//	public void setGenreId(Long genreId) {
//		this.genreId = genreId;
//	}
//
//	
//
//	
//}

package com.LoginRegister.example.book;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.LoginRegister.example.author.AuthorDTO;
import com.LoginRegister.example.category.CategoryDTO;
import com.LoginRegister.example.userdto.UserDTO;

public class BookDTO {

    @NotNull(message = "Title is required")
    private String title;

    private Long bookId;

    private Long userId;
    
    private AuthorDTO author;
    private CategoryDTO genre;

    private Long authorId;
    private Long genreId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "ISBN is required")
    private String isbn;

    private UserDTO user;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public CategoryDTO getGenre() {
        return genre;
    }

    public void setGenre(CategoryDTO genre) {
        this.genre = genre;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
}
