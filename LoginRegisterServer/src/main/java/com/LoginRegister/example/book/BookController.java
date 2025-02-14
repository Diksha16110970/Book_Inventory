package com.LoginRegister.example.book;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.LoginRegister.example.author.AuthorController.ErrorResponse;
import com.LoginRegister.example.repository.UsersRepo;
import com.LoginRegister.example.security.JwtUtil;

//import com.pinnacle.login.model.Book;
//import com.pinnacle.login.repository.UserRepository;
//import com.pinnacle.login.service.BookService;
//import com.pinnacle.login.userdto.BookDTO;
//import com.pinnacle.login.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsersRepo userRepository;

    // Create a new book
    @PostMapping("/addbook")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        token = token.substring(7);
        Long userId = jwtUtil.extractUserId(token);

        // Ensure bookDTO contains a valid userId
        bookDTO.setUserId(userId); // Set userId for the book

        // Create the book from the DTO and save it
        BookDTO createdBook = bookService.saveBook(bookDTO);

        return ResponseEntity.ok(createdBook);
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // Get books by user ID
    //@GetMapping("/user/{userId}")
//    @GetMapping("/getbookbyuserid/{userId}")
//    public ResponseEntity<List<BookDTO>> getBooksByUserId(@PathVariable Long userId) {
//        List<BookDTO> books = bookService.getBooksByUserId(userId);
//        if (books.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(books);
//    }
    
    @GetMapping("/getbookbyuserid/{userId}")
    public ResponseEntity<List<BookDTO>> getBooksByUserId(
            @PathVariable Long userId, HttpServletRequest request) {
        // Extract token from the Authorization header
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // No token or invalid token
        }

        token = token.substring(7); // Remove "Bearer " prefix

        // Validate token
        if (!jwtUtil.isValidToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Invalid token
        }

        // Extract user ID from token
        Long tokenUserId = jwtUtil.extractUserId(token);
        if (!tokenUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Token's userId doesn't match requested userId
        }

        try {
            List<BookDTO> books = bookService.getBooksByUserId(userId);
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Get all books
    @GetMapping("/getallBooks")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Update a book by ID
//    @PutMapping("updatebook/{id}")
//    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
//        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
//    }
    
    @PutMapping("/updatebook/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        try {
            BookDTO updatedBook = bookService.updateBook(id, bookDTO);
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // Delete a book by ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/book-count")
    public ResponseEntity<?> getBookCount() {
        try {
            long count = bookService.getBookCount();  // Call the service to get the count
            return ResponseEntity.ok(count);  // Return the count as a response
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Failed to fetch book count: " + e.getMessage()));
        }
    }
}