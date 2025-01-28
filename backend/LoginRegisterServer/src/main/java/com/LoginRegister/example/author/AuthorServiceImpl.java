//package com.LoginRegister.example.author;
//
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.LoginRegister.example.entity.Users;
//import com.LoginRegister.example.repository.UsersRepo;
//
//@Service
//public class AuthorServiceImpl implements IAuthorService {
//
//    @Autowired
//    private AuthorRepository authorRepository;
//
//    @Autowired
//    private UsersRepo usersRepo;
//
//    @Override
//    public Author addAuthor(AuthorDTO authorDTO) {
//        if (authorDTO.getUser_id() == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//
//        Users user = usersRepo.findById(authorDTO.getUser_id())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorDTO.getUser_id()));
//
//        if (authorRepository.findByName(authorDTO.getName()).isPresent()) {
//            throw new IllegalArgumentException("Author with this name already exists!");
//        }
//
//        Author author = new Author();
//        author.setName(authorDTO.getName());
//        author.setUser(user);
//
//        return authorRepository.save(author);
//    }
//
//    @Override
//    public Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
//        Author existingAuthor = authorRepository.findById(authorId)
//                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
//
//        if (authorDTO.getName() != null) {
//            if (authorRepository.findByName(authorDTO.getName()).isPresent() &&
//                !existingAuthor.getName().equals(authorDTO.getName())) {
//                throw new IllegalArgumentException("Author with this name already exists!");
//            }
//            existingAuthor.setName(authorDTO.getName());
//        }
//
//        if (authorDTO.getUser_id() != null) {
//            Users user = usersRepo.findById(authorDTO.getUser_id())
//                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorDTO.getUser_id()));
//            existingAuthor.setUser(user);
//        }
//
//        return authorRepository.save(existingAuthor);
//    }
//
//    @Override
//    public void deleteAuthor(Long authorId) {
//        Author author = authorRepository.findById(authorId)
//                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
//
//        authorRepository.delete(author);
//    }
//
//    @Override
//    public List<AuthorDTO> getAllAuthors() {
//        List<Author> authors = authorRepository.findAll();
//
//        return authors.stream().map(author -> {
//            AuthorDTO dto = new AuthorDTO();
//            dto.setAuthorId(author.getAuthorId());
//            dto.setName(author.getName());
//            dto.setUser_id(author.getUser().getUser_id());
//            return dto;
//        }).toList();
//    }
//    @Override
//    public List<Author> getAuthorsByUserId(Long userId) {
//        Users user = usersRepo.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
//
//        return authorRepository.findByUser(user);
//    }
//}


package com.LoginRegister.example.author;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.repository.UsersRepo;
import com.LoginRegister.example.author.AuthorRepository;

@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public Author addAuthor(AuthorDTO authorDTO) {
        // Ensure userId is not null
        if (authorDTO.getUser_id() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Find the user using user_id
        Users user = usersRepo.findById(authorDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorDTO.getUser_id()));

        // Check if the author name already exists
        if (authorRepository.findByName(authorDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Author with this name already exists!");
        }

        // Create a new Author object
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setUser(user); // Associate the user with the author

        // Save and return the author
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
        // Find the existing author by ID
        Author existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));

        // Update author details
        if (authorDTO.getName() != null) {
            if (authorRepository.findByName(authorDTO.getName()).isPresent() &&
                !existingAuthor.getName().equals(authorDTO.getName())) {
                throw new IllegalArgumentException("Author with this name already exists!");
            }
            existingAuthor.setName(authorDTO.getName());
        }

        // Update user association
        if (authorDTO.getUser_id() != null) {
            Users user = usersRepo.findById(authorDTO.getUser_id())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorDTO.getUser_id()));
            existingAuthor.setUser(user);
        }
        Users user = usersRepo.findById(authorDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorDTO.getUser_id()));

        Author author = new Author();
        //author.setName(authorDTO.getName());
        author.setUser(user); 

        // Save and return the updated author
        return authorRepository.save(existingAuthor);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        // Check if the author exists
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));

        // Delete the author
        authorRepository.delete(author);
    }

//    @Override
//    public List<AuthorDTO> getAllAuthors() {
//        // Fetch all authors
//        List<Author> authors = authorRepository.findAll();
//
//        // Convert each Author entity to AuthorDTO
//        return authors.stream().map(author -> {
//            AuthorDTO dto = new AuthorDTO();
//            dto.setAuthorId(author.getAuthorId());
//            dto.setName(author.getName());
//            dto.setUser_id(author.getUser().getUser_id());
//          
//            return dto;
//        }).toList();
//    }
    
    @Override
    public List<AuthorDTO> getAllAuthors() {
        // Fetch all authors
        List<Author> authors = authorRepository.findAll();

        // Check if authors list is empty
        if (authors.isEmpty()) {
            throw new IllegalArgumentException("No authors found!");
        }

        // Convert each Author entity to AuthorDTO
        return authors.stream().map(author -> {
            AuthorDTO dto = new AuthorDTO();
            dto.setAuthorId(author.getAuthorId());
            dto.setName(author.getName());

            // Check if the associated user exists before setting
            if (author.getUser() != null) {
                //dto.setUser_id(author.getUser().getUser_id());
                dto.setUser(author.getUser());
            } else {
                throw new IllegalArgumentException("User not found for author: " + author.getAuthorId());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    
    @Override
    public List<Author> getAuthorsByUserId(Long userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return authorRepository.findByUser(user);
    }
    public long getAuthorCount() {
        try {
            return authorRepository.count();  // Return the count of authors
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch author count", e);  // Handle any exception
        }
    }
    
}

