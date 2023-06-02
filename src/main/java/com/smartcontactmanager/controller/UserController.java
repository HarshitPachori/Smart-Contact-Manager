package com.smartcontactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.dao.ContactRepo;
import com.smartcontactmanager.dao.UserRepo;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.models.Contact;
import com.smartcontactmanager.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;

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
    public String processContactForm(
            @ModelAttribute Contact contact,
            @RequestParam("profileImage") MultipartFile file,
            Principal principal, HttpSession session) {

        try {
            String name = principal.getName();
            User user = userRepo.getUserByUserName(name);

            // processing and uploading file
            if (file.isEmpty()) {
                // is the file is empty then try ur msg
                System.out.println("image is empty...");
            } else {
                // upload file to folder and update the name to contact
                // set the imahe to contac t object
                contact.setImageUrl(file.getOriginalFilename());
                // to upload file to folder
                File saveFile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            // bcz of bydirectional mapping
            contact.setUser(user);

            // user ki table me contact daalne ke liye
            user.getContacts().add(contact);

            userRepo.save(user);
            System.out.println("Data = " + contact);
            System.out.println("Added to database ");
            // message success .......
            session.setAttribute("msg", new Message("Your contact is added !! Add more...", "success"));
            // remove this msg from the session................

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            // error message ........
            session.setAttribute("msg", new Message("Something Went Wrong !! Try Again...", "danger"));

        }
        return "normal/add_contact_form";
    }

    // show contacts handler
    // principal se bhi nikaal sakte hai conatct ki list
    // per page 5 contacts = 5[n]
    // current page = 0 [page]
    // @GetMapping("/show-contacts") // agar pagination nahi chaiye toh ise use karo
    @GetMapping("/show-contacts/{page}") // use for pagination we use path variable page and use it in below
    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {
        model.addAttribute("title", "Show User Contacts");
        String username = principal.getName(); // email
        User user = this.userRepo.getUserByUserName(username);
        // pagination 5 contacts per page
        // pageable is the parent interface of pagerequest
        Pageable pageable = PageRequest.of(page, 4);
        Page<Contact> contacts = this.contactRepo.findContactsByUser(user.getId(), pageable);
        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());
        return "normal/show_contacts";
    }
}
