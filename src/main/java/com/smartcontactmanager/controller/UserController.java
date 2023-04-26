package com.smartcontactmanager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactmanager.dao.UserRepo;
import com.smartcontactmanager.models.Contact;
import com.smartcontactmanager.models.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    // har baar chalega ye methoid or add krdega sabme ye data
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        // added logged in user to all handlers

        // get the username from session
        String userName = principal.getName();
        System.out.println("username  = " + userName);

        // get the user using username
        User user = userRepo.getUserByUserName(userName);
        System.out.println(user);

        // send user data to the view
        model.addAttribute("user", user);
    }

    // principal to get user id or email or unique identifier
    @RequestMapping("/dashboard")
    public String index() {
        return "normal/user_dashboard";
    }

    // add from handler
    @GetMapping("/add-contact")
    public String addContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    // processing contact form
    @PostMapping("/process-contact")
    public String processContactForm(@ModelAttribute Contact contact, Principal principal) {
        String name = principal.getName();
        User user = userRepo.getUserByUserName(name);
        // bcz of bydirectional mapping
        contact.setUser(user);
        // user ki table me contact daalne ke liye
        user.getContacts().add(contact);
        userRepo.save(user);
        System.out.println("Data = " + contact);
        System.out.println("Added to database ");
        return "normal/add_contact_form";
    }
}
