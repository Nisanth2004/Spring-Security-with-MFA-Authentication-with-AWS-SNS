package com.nisanth.service.impl;

import com.nisanth.model.Otp;
import com.nisanth.model.User;
import com.nisanth.respository.UserRepository;
import com.nisanth.service.UserServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class UserService implements UserServiceContract {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public String authenticateUser(User user, Model model) {

        // check te user exitst to authenticate
        Optional<User> findUser=userRepository.findUserByEmail(user.getEmail());
        if(findUser.isPresent())
        {
            User checkedUser=findUser.get();

            // check the pasword is also correct
        }
        else {
            model.addAttribute("message","Bad Credentials");
            return "auth";
        }
        return null;
    }

    @Override
    public boolean checkOtpValidity(Otp otp) {
        return false;
    }
}
