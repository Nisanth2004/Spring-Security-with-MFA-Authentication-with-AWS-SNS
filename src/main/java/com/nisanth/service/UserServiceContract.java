package com.nisanth.service;

import com.nisanth.model.Otp;
import com.nisanth.model.User;
import org.springframework.ui.Model;

public interface UserServiceContract {

    public void saveUser(User user);
    public String authenticateUser(User user, Model model);
    public boolean checkOtpValidity(Otp otp);
}
