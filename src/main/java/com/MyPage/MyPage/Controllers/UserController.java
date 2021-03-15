package com.MyPage.MyPage.Controllers;

import com.MyPage.MyPage.Models.*;
import com.MyPage.MyPage.Repositories.*;
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

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private HistoryRepository historyRepository;

    private int groupId;


    @GetMapping()
    public String user(Model model, Principal principal){
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

            Set<Balance> balances = balanceRepository.findByUser1AndSquad(user, oneSquad);
            modelAndView.addObject("balances", balances);

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
    public String addBalance(@RequestParam(value = "list", required = false) int[] list,
                             @RequestParam(value = "inputValue") String inputValue,
                             @RequestParam(required = false) String inputComment,
                             Model model,
                             Principal principal) {
        try {
            User loogedUser = userRepository.findByEmail(principal.getName());
            float f = Float.parseFloat(inputValue);
            System.out.println(f);
            System.out.println(list.length);

            if (list.length != 0) {
                float trueValue = f / list.length;

                for (int i = 0; i < list.length; i++) {

                    if (!loogedUser.getEmail().equals(userRepository.findById(list[i]).getEmail())) {

                        History history1 = new History(inputComment, trueValue, new Date(), loogedUser, userRepository.findById(list[i]), squadRepository.findById(groupId), "In Progress");
                        historyRepository.save(history1);

                        BalanceConfirmation balanceConfirmation = new BalanceConfirmation(trueValue, inputComment, new Date(), loogedUser, userRepository.findById(list[i]), squadRepository.findById(groupId), history1);
                        balanceConfirmationRepository.save(balanceConfirmation);
                    }
                }
            }
            return "redirect:/user/" + groupId;
        } catch (NumberFormatException numberFormatException) {
            String format = "Check if the value is correct.";
            model.addAttribute("wrongFormat", format);
            System.out.println(format);
            return "redirect:/user/" + groupId;
        }
    }

    @PostMapping("/acceptOrDelete")
    public String acceptOrDeleteBalance(@RequestParam(value = "accept", required = false) String accept, @RequestParam(value = "delete", required = false) String delete, Model model, Principal principal){


        if (accept != null) {
            int id = Integer.parseInt(accept);
            BalanceConfirmation balanceConfirmation = balanceConfirmationRepository.findById(id);
            History history = historyRepository.findById(balanceConfirmation.getHistory().getIdHistory());

            float balanceValue = balanceConfirmation.getValue();

            Balance firstUsersBalance = balanceRepository.findBySquadAndUser1AndUser2(balanceConfirmation.getSquad(), balanceConfirmation.getUser1(), balanceConfirmation.getUser2());
            Balance secondUsersBalance = balanceRepository.findBySquadAndUser1AndUser2(balanceConfirmation.getSquad(), balanceConfirmation.getUser2(), balanceConfirmation.getUser1());

            System.out.println(firstUsersBalance);

            if (firstUsersBalance != null){
                float presentValue = firstUsersBalance.getValue();
                presentValue -= balanceValue;
                firstUsersBalance.setValue(presentValue);
                float presentValue2 = secondUsersBalance.getValue();
                presentValue2 += balanceValue;
                secondUsersBalance.setValue(presentValue2);
                balanceRepository.save(firstUsersBalance);
                balanceRepository.save(secondUsersBalance);
            }
            else {
                Balance balance1 = new Balance(-balanceValue, balanceConfirmation.getUser1(), balanceConfirmation.getUser2(), balanceConfirmation.getSquad());
                Balance balance2 = new Balance(balanceValue, balanceConfirmation.getUser2(), balanceConfirmation.getUser1(), balanceConfirmation.getSquad());
                balanceRepository.save(balance1);
                balanceRepository.save(balance2);

            }
            history.setState("accepted");
            historyRepository.save(history);
            balanceConfirmationRepository.delete(balanceConfirmation);
        }
        else if (delete != null){
            int id = Integer.parseInt(delete);
            BalanceConfirmation balanceConfirmation = balanceConfirmationRepository.findById(id);
            History history = historyRepository.findById(balanceConfirmation.getHistory().getIdHistory());

            balanceConfirmationRepository.delete(balanceConfirmation);
            history.setState("rejected");
            historyRepository.save(history);
        }

        return "redirect:/user";
    }

}
