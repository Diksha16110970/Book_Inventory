package com.LoginRegister.example.author;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IAuthorService {

    Author addAuthor(AuthorDTO authorDTO);
    Author updateAuthor(Long authorId, AuthorDTO authorDTO);
    void deleteAuthor(Long authorId);
    List<AuthorDTO> getAllAuthors();
    List<Author> getAuthorsByUserId(Long userId);
	Map<String, Integer> saveAuthorsFromCSV(MultipartFile file, Long userId);
   

}




