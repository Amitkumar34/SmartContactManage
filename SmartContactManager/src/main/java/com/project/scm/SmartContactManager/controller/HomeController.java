package com.project.scm.SmartContactManager.controller;

import com.project.scm.SmartContactManager.dao.User;
import com.project.scm.SmartContactManager.services.impl.UserServiceImpl;
import com.project.scm.SmartContactManager.utils.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String main(){
        return "redirect:/home";
    }
    //Model - sends data to Page
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home: Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About: Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup: Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login: Smart Contact Manager");
        return "login";
    }

    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("userx") User user, BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
        try {
            System.out.println("Terms & Conditions accepted: " + agreement);

            System.out.println(user);
            if (!agreement) throw new Exception("Terms & Conditions not accepted");
            if(result.hasErrors()){
                model.addAttribute("user", user);
                return "redirect:/signup";
            }
            user.setEnabled(true);
            user.setImageUrl("");

            System.out.println(user);

            userService.saveUser(user);

            model.addAttribute("title", "Register: Smart Contact Manager");
            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("alert-success", "Successfully Registered !!"));
        } catch (Exception e) {
            e.printStackTrace();
            user.setEmail("s@g.om");
            model.addAttribute("userx", user);
            session.setAttribute("message", new Message("alert-danger", "Something went wrong! " + e.getMessage()));
        }
        return "redirect:/signup";
    }
}
