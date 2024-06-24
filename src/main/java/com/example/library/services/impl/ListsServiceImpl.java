package com.example.library.services.impl;

import com.example.library.models.entities.Book;
import com.example.library.models.entities.Lists;
import com.example.library.models.entities.User;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.ListsRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.ListsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListsServiceImpl implements ListsService {

    private final ListsRepository listsRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;


    @Autowired
    public ListsServiceImpl(ListsRepository listsRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.listsRepository = listsRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Lists createList(String username, String listName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));


        Lists bookList = new Lists();
        bookList.setName(listName);
        bookList.setUser(user);

        return listsRepository.save(bookList);
    }


    @Override
    public List<Lists> getUserList(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return listsRepository.findByUser(user);
    }


    @Override
    public void addBookToListByName(Long listId, String bookName) {
        Lists bookList = listsRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));


        Optional<Book> optionalBook = bookRepository.findByTitle(bookName);

        Book book = optionalBook.orElseThrow(() -> new EntityNotFoundException("Book not found with title: " + bookName));


        if (book == null) {
            throw new EntityNotFoundException("Book not found with title: " + bookName);
        }

        bookList.getBooks().add(book);
        listsRepository.save(bookList);
    }


    @Override
    public Lists getListById(Long listId) {
        return listsRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("List not found with id: " + listId));
    }
}
