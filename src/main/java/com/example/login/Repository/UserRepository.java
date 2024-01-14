package com.example.login.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login.Model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
