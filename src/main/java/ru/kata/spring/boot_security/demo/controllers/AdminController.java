package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleService roleService;

    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String viewAdminPage(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("userInf", userService.getUserById(user.getId()));
        model.addAttribute("users", userService.getAllUsers());

        return "admin";
    }

    //    @GetMapping(value = "/showPageToAddUser")
//    public String showPageToAddUser(@ModelAttribute("user") User user) {
//        return "new_user";
//    }

//    @GetMapping()
//    public String showPageToAddUser(@ModelAttribute("user") User user) {
//        return "new_user";
//    }

    @PostMapping(value = "/createNewUser")
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/new_user";
//        }

        userService.addNewUser(user);

        return "redirect:/admin";
    }

//    @GetMapping(value = "/edit/{id}")
//    public String showUserEditPage(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//
//        return "edit_user";
//    }

    @PatchMapping(value = "/saveUpdatedUser/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit_user";
        }

        userService.updateUser(user);

        return "redirect:/admin";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);

        return "redirect:/admin";
    }

}