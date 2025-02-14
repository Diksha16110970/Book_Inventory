//package com.LoginRegister.example.author;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AuthorController {
//
//    @Autowired
//    private IAuthorService authorService;
//
//    @PostMapping("/addauthor")
//    public ResponseEntity<?> addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
//        try {
//            Author author = authorService.addAuthor(authorDTO);
//            return ResponseEntity.status(201).body(author);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    @PutMapping("/updateauthor/{id}")
//    public ResponseEntity<?> updateAuthor(
//            @PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
//        try {
//            Author updatedAuthor = authorService.updateAuthor(id, authorDTO);
//            return ResponseEntity.ok(updatedAuthor);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    @DeleteMapping("/deleteauthor/{id}")
//    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
//        try {
//            authorService.deleteAuthor(id);
//            return ResponseEntity.ok("Author deleted successfully!");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    @GetMapping("/getallauthors")
//    public ResponseEntity<?> getAllAuthors() {
//        try {
//            List<AuthorDTO> authors = authorService.getAllAuthors();
//            return ResponseEntity.ok(authors);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(new ErrorResponse("Failed to fetch authors: " + e.getMessage()));
//        }
//    }
//
//    public static class ErrorResponse {
//        private String message;
//
//        public ErrorResponse(String message) {
//            this.message = message;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//    }
//}

package com.LoginRegister.example.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.LoginRegister.example.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthorController {

    @Autowired
    private IAuthorService authorService;
    
    @Autowired
    private AuthorRepository authorRepository; 
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/addauthor")
    public ResponseEntity<?> addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        try {
            Author author = authorService.addAuthor(authorDTO);
            return ResponseEntity.status(201).body(author);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/updateauthor/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, authorDTO);
            return ResponseEntity.ok(updatedAuthor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/deleteauthor/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok("Author deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/getallauthors")
    public ResponseEntity<?> getAllAuthors() {
        try {
            List<AuthorDTO> authors = authorService.getAllAuthors();
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Failed to fetch authors: " + e.getMessage()));
        }
    }
    
    @GetMapping("/authoruser/{userId}")
    public ResponseEntity<List<Author>> getAuthorsByUserId(
            @PathVariable("userId") Long userId, HttpServletRequest request) {
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

        // Extract user ID from token (or verify against a stored user ID)
        Long tokenUserId = jwtUtil.extractUserId(token);
        if (!tokenUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Token's userId doesn't match requested userId
        }

        try {
            List<Author> authors = authorService.getAuthorsByUserId(userId);
            if (authors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/author-count")  // Endpoint to fetch author count
    public ResponseEntity<?> getAuthorCount() {
        try {
            long count = authorRepository.count();  // Count the number of authors in the database
            return ResponseEntity.ok(count);  // Return the count as a response
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Failed to fetch author count: " + e.getMessage()));
        }
    }


    
    @PostMapping("/upload-authors/{userId}")
    public ResponseEntity<?> uploadCSV(
            @RequestParam("file") MultipartFile file, 
            @PathVariable("userId") Long userId,
            @RequestHeader("Authorization") String authorizationHeader) {
        
        try {
            // Extract token from Authorization header
            String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                    ? authorizationHeader.substring(7)
                    : null;

            // Validate the token
            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(401).body(Map.of(
                        "status", 401,
                        "message", "Invalid or missing token"
                ));
            }

            // Validate CSV file format
            if (file.isEmpty() || !CSVHelper.hasCSVFormat(file)) {
                return ResponseEntity.status(400).body(Map.of(
                        "status", 400,
                        "message", "Invalid or empty CSV file"
                ));
            }

            // Validate user ID from the token matches the userId parameter
            Long tokenUserId = jwtUtil.extractUserId(token);
            if (!tokenUserId.equals(userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // Save authors from CSV and get counts
            Map<String, Integer> result = authorService.saveAuthorsFromCSV(file, userId);

            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "message", "CSV processed successfully",
                    "uniqueRecordsAdded", result.get("uniqueRecords"),
                    "duplicateRecordsSkipped", result.get("duplicateRecords")
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Error processing CSV file: " + e.getMessage()
            ));
        }
    }
    
    public static class ErrorResponse {
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


