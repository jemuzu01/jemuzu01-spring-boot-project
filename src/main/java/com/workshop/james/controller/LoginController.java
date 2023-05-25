package com.workshop.james.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView example() {
        ModelAndView modelAndView = new ModelAndView("index");// sets the view name
        return modelAndView;
    }



}
