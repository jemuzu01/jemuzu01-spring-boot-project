package com.workshop.james.repository;

import com.workshop.james.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String username);

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
     User findByUserEmail(@Param("email") String email);


}
