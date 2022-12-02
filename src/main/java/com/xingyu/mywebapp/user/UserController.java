package com.xingyu.mywebapp.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showAllUsers(Model model){
        List<User> allUsers = service.listAll();
        model.addAttribute("allUsers",allUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String newUserForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add new user");
        return "user_form";
    }
    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra){
        service.saveUser(user);
        ra.addFlashAttribute("message", "User saved succeccfully!");
        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, RedirectAttributes ra,Model model) {
        try {
            User user = service.editUser(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user:"+id);
            return "user_form";

        } catch(UserNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message","user delete successfully");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";


    }


}
