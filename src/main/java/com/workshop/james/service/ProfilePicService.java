package com.workshop.james.service;

import com.workshop.james.model.ProfilePic;
import com.workshop.james.model.User;
import com.workshop.james.repository.ProfilePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProfilePicService {

    @Autowired
    ProfilePicRepository profilePicRepository;


    public ProfilePic addPic(ProfilePic profilePic, int userId,MultipartFile file) {
        Optional<ProfilePic> existingProfilePic = Optional.ofNullable(profilePicRepository.findByUserId(userId));
        try {
            String originalFilename = file.getOriginalFilename();
            User user = new User();

            // Create a new file instance with the desired file path and name
            File saveFile = new ClassPathResource("static/uploads").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            if (existingProfilePic.isPresent()) {
                // ID exists, update the profilePic
                ProfilePic updatedProfilePic = existingProfilePic.get();
                updatedProfilePic.setName(originalFilename);
                return profilePicRepository.save(updatedProfilePic);
            } else {
                profilePic.setName(originalFilename);
                user.setId(userId);
                profilePic.setUser(user); // Set the user ID if needed
                return profilePicRepository.save(profilePic);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return profilePic;
    }


    public Iterable<ProfilePic> getAllRecords() {
        return profilePicRepository.findAll();
    }

    public ProfilePic getProfileImgByUserId(int userId) {
        return profilePicRepository.findByUserId(userId);
    }
}
