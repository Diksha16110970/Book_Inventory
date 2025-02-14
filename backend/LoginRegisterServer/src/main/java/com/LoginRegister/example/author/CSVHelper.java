package com.LoginRegister.example.author;

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

import com.LoginRegister.example.entity.Users;

import io.jsonwebtoken.io.IOException;

//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.LoginRegister.example.entity.Users;
//
//import io.jsonwebtoken.io.IOException;
//
//public class CSVHelper {
//
//    public static String TYPE = "text/csv";
//    
//    // Check if the file has a CSV format
//    public static boolean hasCSVFormat(MultipartFile file) {
//        return TYPE.equals(file.getContentType()) || file.getOriginalFilename().endsWith(".csv");
//    }
//
//    public static List<Author> csvToAuthors(InputStream is, Users user, AuthorRepository authorRepository) throws java.io.IOException {
//        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//             CSVParser csvParser = new CSVParser(fileReader, 
//                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
//
//            List<Author> authors = new ArrayList<>();
//            for (CSVRecord csvRecord : csvParser) {
//                String authorName = csvRecord.get("name");
//
//                // Check if the author already exists in the database
//                if (authorRepository.findByName(authorName).isPresent()) {
//                    System.out.println("Skipping duplicate author: " + authorName);
//                    continue; // Skip adding this author to avoid duplicates
//                }
//
//                Author author = new Author();
//                author.setName(authorName);
//                
//                author.setUser(user); // Set user from authenticated request
//
//                authors.add(author);
//            }
//            return authors;
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
//        }
//    }
//}

public class CSVHelper {

    public static String TYPE = "text/csv";
    
    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType()) || file.getOriginalFilename().endsWith(".csv");
    }

    public static Map<String, Integer> csvToAuthors(InputStream is, Users user, AuthorRepository authorRepository) throws IOException, java.io.IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, 
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Author> authorsToSave = new ArrayList<>();
            int uniqueRecords = 0;
            int duplicateRecords = 0;

            for (CSVRecord csvRecord : csvParser) {
                String authorName = csvRecord.get("name");

                Optional<Author> existingAuthor = authorRepository.findByName(authorName);

                if (existingAuthor.isPresent()) {
                    System.out.println("Skipping duplicate author: " + authorName);
                    duplicateRecords++;
                    continue;
                }

                // Create new Author record
                Author author = new Author();
                author.setName(authorName);
                author.setUser(user);
                
                authorsToSave.add(author);
                uniqueRecords++;
            }

            // Save unique authors
            if (!authorsToSave.isEmpty()) {
                authorRepository.saveAll(authorsToSave);
            }

            // Return counts
            Map<String, Integer> recordCounts = new HashMap<>();
            recordCounts.put("uniqueRecords", uniqueRecords);
            recordCounts.put("duplicateRecords", duplicateRecords);

            return recordCounts;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}

