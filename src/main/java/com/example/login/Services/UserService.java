package com.example.login.Services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.login.Model.User;
import com.example.login.Model.DTOs.UserCreateDTO;
import com.example.login.Model.DTOs.UserUpdateDTO;
import com.example.login.Model.ENUMNs.ProfileEnum;
import com.example.login.Repository.UserRepository;
import com.example.login.Security.UserSpringSecurity;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow((() -> new RuntimeException(
        "User not found! Id:" + id + ", tipo: " + User.class.getName())));
    }

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(passwordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("User not found!" + id + " does not match any user");
        }
    }   

    public User fromDTO(@Valid UserCreateDTO obj) {
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        user.setEmail(obj.getEmail());
        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO obj) {
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user;
    }
    
}
