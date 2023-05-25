package com.workshop.james.service;

import com.workshop.james.model.ProfilePic;
import com.workshop.james.model.User;
import com.workshop.james.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void addUser(User userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
    }
    public int getID(String username)
    {
        Optional<User> userID = userRepository.findByName(username);
        int userId = userID.map(User::getId).orElse(null);
        return userId;
    }

    public Iterable<User> EmpRecords() {
        return userRepository.findAll();
    }

}
