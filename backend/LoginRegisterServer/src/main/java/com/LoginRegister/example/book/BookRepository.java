package com.LoginRegister.example.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query("SELECT b FROM Book b WHERE b.user.id = :userId")
    List<Book> findByUserId(@Param("userId") Long userId);

	Optional<Book> findByIsbn(String isbn);
	
}