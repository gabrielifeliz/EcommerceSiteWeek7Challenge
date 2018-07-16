package com.example.demo.controllers;

import com.example.demo.models.AppRole;
import com.example.demo.models.AppUser;
import com.example.demo.repositories.AppRoleRepository;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    AppRoleRepository roles;

    @Autowired
    AppUserRepository users;

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loggedIn() {
        return "redirect:/items/myitems";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new AppUser());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") AppUser user,
            BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "redirect:/items/myitems";
    }

    @RequestMapping("/userlist")
    public String userList(Model model)
    {
        model.addAttribute("users", users.findAll());
        return "userlist";
    }

    @RequestMapping("/delete/{id}")
    public  String deleteUser(@PathVariable("id") long id){
        users.deleteById(id);
        return "redirect:/items/";
    }

    @PostConstruct
    public void loadData(){

        AppRole admin = new AppRole("ADMIN");
        roles.save(admin);

        AppUser adminLogin = new AppUser("Gabriel", "Feliz",
                "gabe@gabe.com", "admin", "admin");
        adminLogin.addRole(admin);
        users.save(adminLogin);

    }

}