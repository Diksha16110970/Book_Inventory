package com.LoginRegister.example.category;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.LoginRegister.example.author.AuthorRepository;
import com.LoginRegister.example.entity.Users;

import io.jsonwebtoken.io.IOException;

public class CSVHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType()) || file.getOriginalFilename().endsWith(".csv");
    }

    public static Map<String, Integer> csvToCategory(InputStream is, Users user, CategoryRepository categoryRepository) throws IOException, java.io.IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, 
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Category> categoriesToSave = new ArrayList<>();
            int uniqueRecords = 0;
            int duplicateRecords = 0;

            for (CSVRecord csvRecord : csvParser) {
                String categoryName = csvRecord.get("name");
                Optional<Category> existingCategory = categoryRepository.findByName(categoryName);

                if (existingCategory.isPresent()) {
                    duplicateRecords++;
                    continue;
                }

                Category category = new Category();
                category.setName(categoryName);
                category.setDescription(csvRecord.get("description"));
                category.setUser(user);

                categoriesToSave.add(category);
                uniqueRecords++;
            }

            if (!categoriesToSave.isEmpty()) {
                categoryRepository.saveAll(categoriesToSave);
            }

            Map<String, Integer> recordCounts = new HashMap<>();
            recordCounts.put("uniqueRecords", uniqueRecords);
            recordCounts.put("duplicateRecords", duplicateRecords);

            return recordCounts;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
