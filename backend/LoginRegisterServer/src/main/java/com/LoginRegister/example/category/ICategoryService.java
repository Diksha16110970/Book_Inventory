package com.LoginRegister.example.category;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.LoginRegister.example.category.Category;
import com.LoginRegister.example.category.CategoryDTO;

public interface ICategoryService {

    Category addCategory(CategoryDTO categoryDTO);
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Long categoryId);
    List<CategoryDTO> getAllCategories();
    List<Category> getCategoriesByUserId(Long userId);
	Map<String, Integer> saveCategoriesFromCSV(MultipartFile file, Long userId);
    
    

}