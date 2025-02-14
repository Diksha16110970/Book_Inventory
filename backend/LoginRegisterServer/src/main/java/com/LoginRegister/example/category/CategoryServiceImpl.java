//package com.LoginRegister.example.category;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.LoginRegister.example.entity.Users;
//import com.LoginRegister.example.repository.UsersRepo;
//import com.LoginRegister.example.security.JwtUtil;
//import com.LoginRegister.example.category.CategoryRepository;
//
//import com.LoginRegister.example.category.ICategoryService;
//import com.LoginRegister.example.category.CategoryDTO;
//
//@Service
//public class CategoryServiceImpl implements ICategoryService {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private UsersRepo usersRepo;
//    
//    @Autowired
//    private JwtUtil jwtUtil; 
//
//    @Override
//    public Category addCategory(CategoryDTO categoryDTO) {
//        // Ensure userId is not null
//        if (categoryDTO.getUser_id() == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//
//        // Find the user using user_id
//        Users user = usersRepo.findById(categoryDTO.getUser_id())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + categoryDTO.getUser_id()));
//
//        // Check if the category name already exists
//        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
//            throw new IllegalArgumentException("Category with this name already exists!");
//        }
//
//        // Create a new Category object
//        Category category = new Category();
//        category.setName(categoryDTO.getName());
//        category.setDescription(categoryDTO.getDescription());
//        category.setUser(user); // Associate the user with the category
//
//        // Save the category to the repository
//        return categoryRepository.save(category);
//    }
//    
//   /* @Override
//    public Category addCategory(CategoryDTO categoryDTO) {
//        if (categoryDTO.getUser_id() == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//
//        Users user = usersRepo.findById(categoryDTO.getUser_id())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + categoryDTO.getUser_id()));
//
//        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
//            throw new IllegalArgumentException("Category with this name already exists!");
//        }
//
//        Category category = new Category();
//        category.setName(categoryDTO.getName());
//        category.setDescription(categoryDTO.getDescription());
//        category.setUser(user);
//
//        Category savedCategory = categoryRepository.save(category);
//
//        // Generate token for the category after saving it
//        String token = jwtUtil.generateToken(savedCategory.getCategoryId().toString());
//
//        // Optional: You can add the token to the response or return it in some way
//        System.out.println("Generated token: " + token);
//
//        return savedCategory;
//    }*/
//    
//    @Override
//    public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) {
//        // Find the existing category by ID
//        Category existingCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
//
//        // Update the fields of the category
//        if (categoryDTO.getName() != null) {
//            if (categoryRepository.findByName(categoryDTO.getName()).isPresent() 
//                && !existingCategory.getName().equals(categoryDTO.getName())) {
//                throw new IllegalArgumentException("Category with this name already exists!");
//            }
//            existingCategory.setName(categoryDTO.getName());
//        }
//
//        if (categoryDTO.getDescription() != null) {
//            existingCategory.setDescription(categoryDTO.getDescription());
//        }
//
//        if (categoryDTO.getUser_id() != null) {
//            Users user = usersRepo.findById(categoryDTO.getUser_id())
//                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + categoryDTO.getUser_id()));
//            existingCategory.setUser(user);
//        }
//
//        // Save the updated category
//        return categoryRepository.save(existingCategory);
//    }
//    @Override
//    public void deleteCategory(Long categoryId) {
//        // Check if the category exists
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
//
//        // Delete the category
//        categoryRepository.delete(category);
//    }
//    @Override
//    public List<CategoryDTO> getAllCategories() {
//        // Fetch all categories from the repository
//        List<Category> categories = categoryRepository.findAll();
//
//        // Convert each Category entity to CategoryDTO
//        return categories.stream().map(category -> {
//            CategoryDTO dto = new CategoryDTO();
////            Category c = new Category();
////            c.setCategoryId(category.getCategoryId());
//            dto.setCategoryId(category.getCategoryId());
//            dto.setName(category.getName());
//            dto.setDescription(category.getDescription());
//            dto.setUser_id(category.getUser().getUser_id()); // Set the user's ID
//            return dto;
//        }).toList();
//    }
//    
//    
//
//
//}


package com.LoginRegister.example.category;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.repository.UsersRepo;
import com.LoginRegister.example.category.CategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getUser_id() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        Users user = usersRepo.findById(categoryDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + categoryDTO.getUser_id()));

        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists!");
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setUser(user);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        if (categoryDTO.getName() != null) {
            existingCategory.setName(categoryDTO.getName());
        }

        if (categoryDTO.getDescription() != null) {
            existingCategory.setDescription(categoryDTO.getDescription());
        }

        if (categoryDTO.getUser_id() != null) {
            Users user = usersRepo.findById(categoryDTO.getUser_id())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + categoryDTO.getUser_id()));
            existingCategory.setUser(user);
        }

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(category.getCategoryId());
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            dto.setUser_id(category.getUser().getUserId());
            return dto;
        }).toList();
    }

    public long getCategoryCount() {
        return categoryRepository.count();
    }
    @Override
    public List<Category> getCategoriesByUserId(Long userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return categoryRepository.findByUser(user);
    }
    @Override
    public Map<String, Integer> saveCategoriesFromCSV(MultipartFile file, Long userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        try {
            InputStream inputStream = file.getInputStream();
            return CSVHelper.csvToCategory(inputStream, user, categoryRepository);
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage());
        }
    }
}

