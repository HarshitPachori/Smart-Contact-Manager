package com.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcontactmanager.dao.UserRepo;
import com.smartcontactmanager.models.User;

@Controller
public class HomeController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        User user = new User();
        user.setName("harshit");
        userRepo.save(user);
        return "working....";
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

}
