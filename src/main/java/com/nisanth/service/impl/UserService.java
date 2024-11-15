package com.nisanth.service.impl;

import com.nisanth.model.Otp;
import com.nisanth.model.User;
import com.nisanth.respository.OtpRepository;
import com.nisanth.respository.UserRepository;
import com.nisanth.service.UserServiceContract;
import com.nisanth.util.MessageUtil;
import com.nisanth.util.OtpUtil;
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

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private MessageUtil messageUtil;  // Inject MessageUtil

    public UserService() {
    }

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
            if(passwordEncoder.matches(user.getPassword(),checkedUser.getPassword()))
            {
                renewOtp(findUser.get());
                return "redirect:/otp-validation";
            }
            else {
                model.addAttribute("message","Bad Credentials");
                return "auth";
            }
        }
        else {
            model.addAttribute("message","Bad Credentials");
            return "auth";
        }

    }

    @Override
    public boolean checkOtpValidity(Otp otp) {
        return false;
    }


    public void renewOtp(User user)
    {
        Optional<Otp> findUser=otpRepository.findOtpByEmail(user.getEmail());
        // generate otp
       String code= OtpUtil .generateOtp();

        messageUtil.sendMessage(user.getPhoneNumber(),"Here is your OTP: "+code);

       // check user is present update the OTP table
        if(findUser.isPresent())
        {
            Otp otp=findUser.get();
            otp.setCode(code);
            otpRepository.save(otp);
        }
        // for new user
        else {

            Otp otp=new Otp();
            otp.setCode(code);
            otp.setEmail(user.getEmail());
            otpRepository.save(otp);

        }
    }
}
