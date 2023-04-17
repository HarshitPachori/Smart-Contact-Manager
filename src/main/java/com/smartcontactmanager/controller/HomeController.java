package com.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcontactmanager.dao.UserRepo;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // for database
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

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "SignUp - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") User user,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
            HttpSession session) {

        try {
            if (!agreement) {
                session.setAttribute("message",
                        new Message("You don't have agreed to the terms and conditions...", "alert-danger"));
                return "redirect:/signup";
            }
            // Check if email already exists in database
            User existingUser = this.userRepo.findByEmail(user.getEmail());
            if (existingUser != null) {
                session.setAttribute("message", new Message("Email already exists...", "alert-danger"));
                return "redirect:/signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");

            User result = this.userRepo.save(user);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("successfully Registered", "alert-success"));
            System.out.println(agreement);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something Went Wrong !! " + e.getMessage(), "alert-danger"));
            return "signup";
        }

        return "redirect:/signup";
    }
}
