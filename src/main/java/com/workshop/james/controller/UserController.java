package com.workshop.james.controller;

import com.workshop.james.config.UserInfoUserDetails;
import com.workshop.james.model.ProfilePic;
import com.workshop.james.model.User;
import com.workshop.james.service.ProfilePicService;
import com.workshop.james.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {



    @Autowired
    UserService userService;

    @Autowired
    ProfilePicService profilePicService;

    @Autowired
    private HttpSession session;




    @PostMapping("/newuser")
    public String addUser(@RequestBody User user)
    {
        userService.addUser(user);
        return "user added to system ";
    }
    @GetMapping("/test")
    public String hello(Authentication authentication)
    {

        String username = authentication.getName();
        String username2 = (String) session.getAttribute("username");


        return username  + username2;
    }
    @GetMapping("/allemp")
    public Iterable<User> showUser()
    {
        return userService.EmpRecords();
    }
    @GetMapping("/home")
    public ModelAndView dashboard(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        String username = authentication.getName();
        int userid = userService.getID(username);
        String strId = Integer.toString(userid);
        ProfilePic img = profilePicService.getProfileImgByUserId(userid);
        String imgName;
        if(img != null)
        {
            imgName = img.getName();
            String imgUrl  = "/uploads/"+imgName;
            session.setAttribute("profileImgName", imgName);
            session.setAttribute("username", strId);
        }
        else
        {
            imgName  = "download.png";
            session.setAttribute("profileImgName", imgName);
        }


        return modelAndView;
    }





}
