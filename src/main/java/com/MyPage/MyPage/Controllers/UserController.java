package com.MyPage.MyPage.Controllers;

import com.MyPage.MyPage.Models.Squad;
import com.MyPage.MyPage.Models.User;
import com.MyPage.MyPage.Repositories.SquadRepository;
import com.MyPage.MyPage.Repositories.UserRepository;
import com.MyPage.MyPage.Services.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.Authenticator;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SquadRepository squadRepository;

    @Autowired
    private CustomUserDetails customUserDetails;

    @GetMapping()
    public String listAllUsers(Model model, Principal principal){
        User listUsers = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", listUsers);
        Set<Squad> squads = listUsers.getSquads();
        model.addAttribute("squads", squads);
        System.out.println(squads);
        return "user";
    }

    @GetMapping("/{id}")
    public ModelAndView groupDetails(@PathVariable(name = "id") int id, Principal principal){
        ModelAndView modelAndView = new ModelAndView("squad_details");
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Squad oneSquad = squadRepository.findById(id);
        Set<Squad> set = user.getSquads();
        if (set.contains(oneSquad)){
            modelAndView.addObject("oneSquad", oneSquad);
            return modelAndView;
        }
        else {
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

}
