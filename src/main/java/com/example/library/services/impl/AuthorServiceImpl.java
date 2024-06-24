package com.example.library.services.impl;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.AuthorImage;
import com.example.library.models.entities.Book;
import com.example.library.repositories.*;
import com.example.library.services.AuthorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final FileStorageService fileStorageService;
    private final AuthorImageRepository authorImageRepository;
    private final ListsRepository listsRepository;
    private final ForReadingRepository forReadingRepository;
    private final ReadRepository readRepository;


    @Value("${file.upload-dir}")
    private String uploadDir;


    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, FileStorageService fileStorageService, AuthorImageRepository authorImageRepository, ListsRepository listsRepository, ForReadingRepository forReadingRepository, ReadRepository readRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.fileStorageService = fileStorageService;
        this.authorImageRepository = authorImageRepository;
        this.listsRepository = listsRepository;
        this.forReadingRepository = forReadingRepository;

        this.readRepository = readRepository;
    }


    @Override
    public void addAuthor(String name, String bio, LocalDate birthdate, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);


        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        author.setBirthdate(birthdate);
        author.setImage(fileName);

        authorRepository.save(author);
    }

    @Override
    public boolean existsByName(String name) {
        return authorRepository.existsByName(name);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            return convertToDto(author);
        } else {
            throw new NoSuchElementException("Author not found with id: " + id);
        }
    }

    @Override
    public AuthorDTO convertToDto(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getBio(),
                author.getBirthdate(),
                author.getImage(),
                author.getImagesList());
    }

    @Override
    public List<Book> getRandomBooksByAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);

        Collections.shuffle(books); // Разбърква списъка с книги
        return books.stream().limit(3).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> getAuthors() {
        return this.authorRepository
                .findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void updateAuthor(Author updatedAuthor) {
        Optional<Author> authorOptional = authorRepository.findById(updatedAuthor.getId());

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();

            author.setName(updatedAuthor.getName());
            author.setBio(updatedAuthor.getBio());
            author.setBirthdate(updatedAuthor.getBirthdate());
            author.setImage(updatedAuthor.getImage());

            authorRepository.save(author);
        } else {
            throw new NoSuchElementException("Author not found with id: " + updatedAuthor.getId());
        }
    }


//    @Override
//    @Transactional
//    public void deleteAuthorById(Long id) {
//        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
//
//        String photoFileName = author.getImage();
//
//
////        readRepository.deleteByBookId(id);
////        forReadingRepository.deleteByBookId(id);
////        listsRepository.deleteByBooksId(id);
////        bookRepository.deleteByAuthorId(id);
////        bookRepository.deleteById(id);
//
//
//
////        Author author = authorRepository.findById(id)
////                .orElseThrow(() -> new RuntimeException("Author not found"));
////
////        // Delete books by author
////        bookService.deleteBooksByAuthorId(author.getId());
////
////        // Now delete the author
////        authorRepository.deleteById(id);
//    }


    @Override
    public Author convertDtoToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();

        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        author.setBio(authorDTO.getBio());
        author.setBirthdate(authorDTO.getBirthdate());
        author.setImage(author.getImage());

        return author;
    }


//

    @Override
    public Author saveAuthors(String name, String bio, LocalDate birthdate,  MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        Author author = new Author();
        author.setName(name);
        author.setBio(fileName);
        author.setBirthdate(birthdate);
        author.setImage(fileName);

        return authorRepository.save(author);
    }

}
