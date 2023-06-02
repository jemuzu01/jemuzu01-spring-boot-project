package com.workshop.james.controller;

import com.workshop.james.model.User;
import com.workshop.james.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    ModelAndView showRegisterForm(){
        ModelAndView modelAndView = new ModelAndView("register-page");
        return  modelAndView;
    }
    @PostMapping("/register")
    ModelAndView registerUser(@RequestParam("name") String name,@RequestParam("password") String password,@RequestParam("email") String email,User user){
        ModelAndView modelAndView = new ModelAndView("register-page");
        userService.addUserWithEmail(user,name,password,email);
        modelAndView.addObject("user",user);
        return  modelAndView;
    }
}
