package com.smartcontactmanager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactmanager.dao.UserRepo;
import com.smartcontactmanager.models.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    // principal to get user id or email or unique identifier
    @RequestMapping("/dashboard")
    public String index(Model model, Principal principal) {

        // get the username from session
        String userName = principal.getName();
        System.out.println("username  = " + userName);

        // get the user using username
        User user = userRepo.getUserByUserName(userName);
        System.out.println(user);

        // send user data to the view
        model.addAttribute("user", user);

        return "normal/user_dashboard";
    }
}
