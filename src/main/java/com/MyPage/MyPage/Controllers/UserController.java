package com.MyPage.MyPage.Controllers;

import com.MyPage.MyPage.Models.BalanceConfirmation;
import com.MyPage.MyPage.Models.Squad;
import com.MyPage.MyPage.Models.User;
import com.MyPage.MyPage.Repositories.BalanceConfirmationRepository;
import com.MyPage.MyPage.Repositories.SquadRepository;
import com.MyPage.MyPage.Repositories.UserRepository;
import com.MyPage.MyPage.Services.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SquadRepository squadRepository;

    @Autowired
    private CustomUserDetails customUserDetails;

    @Autowired
    private BalanceConfirmationRepository balanceConfirmationRepository;

    private int groupId;


    @GetMapping()
    public String listAllUsers(Model model, Principal principal){
        User listUsers = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", listUsers);
        Set<Squad> squads = listUsers.getSquads();
        model.addAttribute("squads", squads);
        Set<BalanceConfirmation> balanceConfirmations = listUsers.getBalanceConfirmations();
        model.addAttribute("balance", balanceConfirmations);
        return "user";
    }

    @GetMapping("/{id}")
    public ModelAndView groupDetails(@PathVariable(name = "id") int id, Principal principal){
        ModelAndView modelAndView = new ModelAndView("squad_details");

        groupId = id;
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Squad oneSquad = squadRepository.findById(id);
        Set<Squad> set = user.getSquads();

        if (set.contains(oneSquad)){
            modelAndView.addObject("oneSquad", oneSquad);
            Set<User> usersInSquad = oneSquad.getUsers();
            modelAndView.addObject("users", usersInSquad);

            return modelAndView;
        }
        else {
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @PostMapping("/addUserToSquad")
    public String addUserToSquad(@RequestParam String squadCode, Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Squad squad = squadRepository.findByCode(squadCode);
        if (squad.getUsers().contains(user)){
            return "redirect:/user";
        }
        if (squad.getCode() == null){
            return "redirect:/user";
        }
        else {
            Set<User> setU = squad.getUsers();
            setU.add(user);
            squad.setUsers(setU);
            squadRepository.save(squad);

            Set<Squad> setS = user.getSquads();
            setS.add(squad);
            user.setSquads(setS);
            userRepository.save(user);
            return "redirect:/user";
        }
    }

    @PostMapping("/createSquad")
    public String createSquad(@RequestParam String squadName, Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Squad squad = new Squad(squadName);
        Set<User> setU = new HashSet<>();
        setU.add(user);
        squad.setUsers(setU);
        squadRepository.save(squad);
        return "redirect:/user";
    }

    @PostMapping("/addBalance")
    public String addBalance(@RequestParam(value = "list", required = false) int[] list, @RequestParam(value = "inputValue", required = false) String inputValue, @RequestParam(required = false) String inputComment, Model model, Principal principal){

        User loogedUser = userRepository.findByEmail(principal.getName());
        float f = Float.parseFloat(inputValue);

        if (list.length != 0){
            float trueValue = f/list.length;

            for (int i = 0; i < list.length; i++){

                BalanceConfirmation balanceConfirmation = new BalanceConfirmation(trueValue, inputComment, new Date(), loogedUser, userRepository.findById(list[i]));
                balanceConfirmationRepository.save(balanceConfirmation);
            }
        }

        return "redirect:/user/" + groupId;
    }

}
