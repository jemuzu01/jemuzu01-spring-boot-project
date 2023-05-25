package com.workshop.james.repository;

import com.workshop.james.model.ProfilePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfilePicRepository extends JpaRepository<ProfilePic, Integer> {

    @Query(value = "SELECT * FROM profile_pic WHERE user_id = :userId", nativeQuery = true)
    ProfilePic findByUserId(@Param("userId") int userId);

}
