package com.example.library.controllers;

import com.example.library.models.entities.Lists;
import com.example.library.services.ListsService;
import com.example.library.util.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//        return "redirect:/lists/" + newList.getId();
        return "redirect:/lists/user-list";

    }



    @PostMapping("/{listId}/bookByName")
    public String addBookToList(@PathVariable Long listId, @RequestParam String bookName, RedirectAttributes redirectAttributes) {

        try {
            listsService.addBookToListByName(listId, bookName);
            return "redirect:/lists/user-list";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "noSuchBook";
        }

    }




    @GetMapping("/{listId}")
    public String viewList(@PathVariable Long listId, Model model) {

        Lists lists = listsService.getListById(listId);
        model.addAttribute("lists", lists);
        return "redirect:/lists/user-list";
    }

}
