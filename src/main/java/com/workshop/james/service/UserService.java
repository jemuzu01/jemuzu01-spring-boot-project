package com.workshop.james.service;

import com.workshop.james.model.User;
import com.workshop.james.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;


    public void addUser(User userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
    }
    public String addUserWithEmail(User userInfo,String name,String password,String email) {
        User existingUser = userRepository.findByUserEmail(userInfo.getEmail());

        if (existingUser != null) {
            return "Email already registered";
        }
        String verificationToken = generateVerificationToken();
        userInfo.setName(name);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setToken(verificationToken);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_ADMIN");
        userInfo.setIsVerified(false);
        userRepository.save(userInfo);
        sendVerificationEmail(userInfo.getEmail(),verificationToken);
        return "user has been saved";
    }
    public int getID(String username)
    {
        Optional<User> userID = userRepository.findByName(username);
        int userId = userID.map(User::getId).orElse(null);
        return userId;
    }
    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    private void sendVerificationEmail(String email, String verificationToken) {
        String subject = "Email Verification";
        String body = "Please click on the link to verify your email: " +
                "http://your-app.com/verify?token=" + verificationToken;
        mailService.sendSimpleEmail(email, subject, body);
    }
    public Iterable<User> EmpRecords() {
        return userRepository.findAll();
    }

}
