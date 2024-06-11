package com.example.library.services;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface AuthorService {

    void addAuthor(String name, String bio,LocalDate birthdate, MultipartFile file) throws IOException;

     boolean existsByName(String name);

    List<Author> findAll();

    AuthorDTO getAuthorById(Long id);

    AuthorDTO convertToDto(Author author);

    List<Book> getRandomBooksByAuthor(Long authorId);

    List<AuthorDTO> getAuthors();

//    void updateAuthor(Long id, String newName, String newBio, LocalDate newBirthdate);

    void updateAuthor(Author author);

    void deleteAuthorById(Long id);

    Author convertDtoToAuthor(AuthorDTO authorDTO);
}
