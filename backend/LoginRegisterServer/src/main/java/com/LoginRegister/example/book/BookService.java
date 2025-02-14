package com.LoginRegister.example.book;


import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.LoginRegister.example.author.Author;
import com.LoginRegister.example.author.AuthorDTO;
import com.LoginRegister.example.author.AuthorRepository;
import com.LoginRegister.example.category.Category;
import com.LoginRegister.example.category.CategoryDTO;
import com.LoginRegister.example.category.CategoryRepository;
import com.LoginRegister.example.entity.Users;
import com.LoginRegister.example.repository.UsersRepo;
import com.LoginRegister.example.userdto.UserDTO;



@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository genreRepository;

    @Autowired
    private UsersRepo userRepository;

   
//    
    
    public BookDTO saveBook(BookDTO bookDTO) {
        // Check if the userId is not null in the bookDTO
        if (bookDTO.getUserId() == null) {
            throw new RuntimeException("User ID is missing in the request");
        }

        // Fetch the User entity using the userId from the bookDTO
        Users user = userRepository.findById(bookDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the Author entity using the authorId from bookDTO
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // Fetch the Category entity using the genreId from bookDTO
        Category category = genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create the Book entity and set the Author, Category, and User objects
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);  // Set the Author object
        book.setCategory(category);  // Set the Category object
        book.setQuantity(bookDTO.getQuantity());
        book.setPrice(bookDTO.getPrice());
        book.setIsbn(bookDTO.getIsbn());
        book.setUser(user);  // Set the User entity
        
        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        book.setCreatedAt(now);
        book.setUpdatedAt(now);

        // Save the book to the repository and return the DTO
        Book savedBook = bookRepository.save(book);
        
        // Assuming convertToDTO is a method that converts Book to BookDTO
        return convertToDTO(savedBook);
    }


    // Get a book by ID
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return convertToDTO(book);
    }

    // Get all books
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//  @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        if (bookDTO.getTitle() != null) {
            existingBook.setTitle(bookDTO.getTitle());
        }

        if (bookDTO.getPrice() != null) {
            existingBook.setPrice(bookDTO.getPrice());
        }

        if (bookDTO.getQuantity() != null) {
            existingBook.setQuantity(bookDTO.getQuantity());
        }

        if (bookDTO.getIsbn() != null) {
            existingBook.setIsbn(bookDTO.getIsbn());
        }

        if (bookDTO.getAuthor() != null && bookDTO.getAuthor().getAuthorId() != null) {
            Author author = authorRepository.findById(bookDTO.getAuthor().getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + bookDTO.getAuthor().getAuthorId()));
            existingBook.setAuthor(author);
        }

        if (bookDTO.getGenre() != null && bookDTO.getGenre().getCategoryId() != null) {
            Category category = genreRepository.findById(bookDTO.getGenre().getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Genre not found with ID: " + bookDTO.getGenre().getCategoryId()));
            existingBook.setCategory(category);
        }

        if (bookDTO.getUser() != null && bookDTO.getUser().getUser_id() != null) {
            Users user = userRepository.findById(bookDTO.getUser().getUser_id())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + bookDTO.getUser().getUser_id()));
            existingBook.setUser(user);
        }

        existingBook.setUpdatedAt(LocalDateTime.now());

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }


    // Delete a book by ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Convert Book entity to BookDTO
    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setQuantity(book.getQuantity());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setIsbn(book.getIsbn());

        // Convert Author and Genre to their DTOs
        AuthorDTO authorDTO = new AuthorDTO();
        if (book.getAuthor() != null) {
            authorDTO.setAuthorId(book.getAuthor().getAuthorId());
            authorDTO.setName(book.getAuthor().getName());
            //authorDTO.setBiography(book.getAuthor().getBiography());
            authorDTO.setUser_id(book.getAuthor().getUser() != null ? book.getAuthor().getUser().getUserId() : null);
            authorDTO.setCreatedAt(book.getAuthor().getCreatedAt());
            authorDTO.setUpdatedAt(book.getAuthor().getUpdatedAt());
        }

        CategoryDTO genreDTO = new CategoryDTO();
        if (book.getCategory() != null) {
            genreDTO.setCategoryId(book.getCategory().getCategoryId());
            genreDTO.setName(book.getCategory().getName());
            genreDTO.setDescription(book.getCategory().getDescription());
            genreDTO.setCreatedAt(book.getCategory().getCreatedAt());
            genreDTO.setUpdatedAt(book.getCategory().getUpdatedAt());
            genreDTO.setUser_id(book.getCategory().getUser() != null ? book.getCategory().getUser().getUserId() : null);
        }

        bookDTO.setAuthor(authorDTO); // Set AuthorDTO
        bookDTO.setGenre(genreDTO);   // Set GenreDTO

        // Convert User to UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(book.getUser().getUserId()); // Ensure the User ID is correctly set
        // If the User entity has more properties that you want to include in the DTO:
        userDTO.setName(book.getUser().getName()); // Assuming name exists
        userDTO.setEmail(book.getUser().getEmail()); // Assuming email exists
        // Continue setting other properties if necessary.

        bookDTO.setUser(userDTO); // Set UserDTO

        return bookDTO;
    }

	

	public List<BookDTO> getBooksByUserId(Long userId) {
		// TODO Auto-generated method stub
		List<Book> authors = bookRepository.findByUserId(userId);
        return authors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}
	public long getBookCount() {
        try {
            return bookRepository.count();  // Return the count of books
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch book count", e);  // Handle any exception
        }
    }
	
}