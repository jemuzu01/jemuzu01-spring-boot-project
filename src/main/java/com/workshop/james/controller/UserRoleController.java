package com.workshop.james.controller;

import com.workshop.james.model.ProfilePic;
import com.workshop.james.model.User;
import com.workshop.james.service.ProfilePicService;
import com.workshop.james.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRoleController {

    @Autowired
    ProfilePicService profilePicService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/test")
    public String hello()
    {
        return "hello ";
    }

    @GetMapping("/image")
    public Iterable<ProfilePic> getAllRecords() {
        return profilePicService.getAllRecords();
    }

    @GetMapping("/image/{uid}")
    public ProfilePic getPic(@PathVariable int uid)
    {
        return profilePicService.getProfileImgByUserId(uid);
    }



    @GetMapping("/profile-pic")
    public ModelAndView profilePic() {
        ModelAndView modelAndView = new ModelAndView("profile-pic");// sets the view name
        return modelAndView;
    }


    @PostMapping("/profile-pic")
    public  ModelAndView uploadPic(@RequestParam("file") MultipartFile file, ProfilePic image, Authentication authentication) throws IOException {
        if (!file.isEmpty()) {

                User u = new User();
                String username = authentication.getName();
                int userid = userService.getID(username);
                profilePicService.addPic(image,userid,file);


        }
        ModelAndView modelAndView = new ModelAndView(("redirect:/user/test"));
        return modelAndView;

    }
}
