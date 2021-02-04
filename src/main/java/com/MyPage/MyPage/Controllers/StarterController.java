package com.MyPage.MyPage.Controllers;

import com.MyPage.MyPage.Models.User;
import com.MyPage.MyPage.Repositories.UserRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StarterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getStartingPage(){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerProcces")
    public String succededRegistration(User user){
        userRepository.save(user);
        return "succededRegistration";
    }
}
