package com.LoginRegister.example.category;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.LoginRegister.example.category.ICategoryService;
import com.LoginRegister.example.security.JwtUtil;
import com.LoginRegister.example.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.LoginRegister.example.category.CategoryDTO;
import com.LoginRegister.example.category.Category;
//import com.LoginRegister.example.security.JwtUtil;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryRepository catrepo;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/addcategory")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = categoryService.addCategory(categoryDTO);
            
            return ResponseEntity.status(201).body(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
//    
    
    @PutMapping("/updatecategory/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/deletecategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id); // Call service layer method
            return ResponseEntity.ok().body("Category deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    @GetMapping("/getallcategories")
    public ResponseEntity<?> getAllCategories() {
        try {
        	
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Failed to fetch categories: " + e.getMessage()));
       }
    }
    
    @GetMapping("/category-count")
    public ResponseEntity<?> getCategoryCount() {
        try {
            long count = catrepo.count();  // Count the number of categories
            return ResponseEntity.ok(count);  // Return the count as a response
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Failed to fetch category count: " + e.getMessage()));
        }
    }
    
    
   

    @GetMapping("/catuser/{userId}")
    public ResponseEntity<List<Category>> getCategoriesByUserId(
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
            List<Category> categories = categoryService.getCategoriesByUserId(userId);
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
 
        @PostMapping("/upload-categories/{userId}")
        public ResponseEntity<?> uploadCSV(
                @RequestParam("file") MultipartFile file,
                @PathVariable("userId") Long userId,
                @RequestHeader("Authorization") String authorizationHeader) {

            try {
                String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                        ? authorizationHeader.substring(7)
                        : null;

                if (token == null || !jwtUtil.isValidToken(token)) {
                    return ResponseEntity.status(401).body(Map.of(
                            "status", 401,
                            "message", "Invalid or missing token"
                    ));
                }

                if (file.isEmpty() || !CSVHelper.hasCSVFormat(file)) {
                    return ResponseEntity.status(400).body(Map.of(
                            "status", 400,
                            "message", "Invalid or empty CSV file"
                    ));
                }

                Long tokenUserId = jwtUtil.extractUserId(token);
                if (!tokenUserId.equals(userId)) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }

                Map<String, Integer> result = categoryService.saveCategoriesFromCSV(file, userId);

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




