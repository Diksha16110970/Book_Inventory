package com.LoginRegister.example.author;

import java.util.List;

public interface IAuthorService {

    Author addAuthor(AuthorDTO authorDTO);
    Author updateAuthor(Long authorId, AuthorDTO authorDTO);
    void deleteAuthor(Long authorId);
    List<AuthorDTO> getAllAuthors();
    List<Author> getAuthorsByUserId(Long userId);
    
}




