package com.example.library.services;

import com.example.library.models.entities.Lists;

import java.util.List;


public interface ListsService {

    Lists createList(String username, String listName);

    List<Lists> getUserList(String username);

    void addBookToListByName(Long listId, String bookName);


    Lists getListById(Long listId);
}
