package com.example.library.controllers;

import com.example.library.models.entities.Lists;
import com.example.library.services.ListsService;
import com.example.library.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lists")
public class ListsController {

    private final ListsService listsService;
    private final CurrentUser currentUser;

    @Autowired
    public ListsController(ListsService listsService, CurrentUser currentUser) {
        this.listsService = listsService;
        this.currentUser = currentUser;
    }

    @GetMapping("/create-page")
    public String getCreateListPage(Model model) {
        String username = currentUser.getUsername();

        List<Lists> userList = listsService.getUserList(username);
        model.addAttribute("userList", userList);

        return "create-list";

    }


    @GetMapping("/user-list")
    public String getListPage(Model model) {
        String username = currentUser.getUsername();

        List<Lists> list = listsService.getUserList(username);
        model.addAttribute("list", list);

        return "list-info";
    }


    @PostMapping("/create")
    public String createList(@RequestParam String listName) {
        String username = currentUser.getUsername();

        Lists newList = listsService.createList(username, listName);

        return "redirect:/lists/" + newList.getId();

    }

//    @PostMapping("/create")
//    public String createList(@RequestParam String listName, Model model) {
//        String username = currentUser.getUsername();
//        Lists newList = listsService.createList(username, listName);
//        List<Lists> userList = listsService.getUserList(username);
//
//        model.addAttribute("userList", userList);
//        model.addAttribute("newListId", newList.getId());
//        return "list-info";
//    }


    @PostMapping("/{listId}/bookByName")
    public String addBookToList(@PathVariable Long listId, @RequestParam String bookName) {
        listsService.addBookToListByName(listId, bookName);
        return "redirect:/lists/user-list";
    }




    @GetMapping("/{listId}")
    public String viewList(@PathVariable Long listId, Model model) {
//        String username = currentUser.getUsername();
//        List<Watchlist> watchlist = watchlistService.getUserWatchlist(username);
//        model.addAttribute("watchlist", watchlist);

        Lists lists = listsService.getListById(listId);
        model.addAttribute("lists", lists);
        return "list-info";
    }

}
