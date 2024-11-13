package com.nisanth.controller;

import com.nisanth.model.User;
import com.nisanth.respository.UserRepository;
import com.nisanth.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user")User user)
    {
        return "registrationForm";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user")User user, Model model) // map all the users to model Attrubute ("user")
    {
        // first chcek the user if already exists
        Optional<User> findUser=userRepository.findUserByEmail(user.getEmail());
        if(findUser.isPresent())
        {
            model.addAttribute("message","User with that email exists");
            return "registrationForm";
        }

        // if the user is not present in db it save the user
        userService.saveUser(user);
        model.addAttribute("message","Saved Successfully");
        return "registrationForm";

    }



    @GetMapping("/user-authentication")
    public String getAuthPage(@ModelAttribute("user") User user)
    {
        return "auth";
    }


    @PostMapping("/user-authentication")
    public String authenticateUser(@ModelAttribute("user") User user,Model model)
    {
        return userService.authenticateUser(user,model);
    }
}
