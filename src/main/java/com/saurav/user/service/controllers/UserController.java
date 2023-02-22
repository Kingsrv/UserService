package com.saurav.user.service.controllers;

import com.saurav.user.service.entities.User;
import com.saurav.user.service.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("Received create user request");
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //get single user

    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);

    }

    //get All users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);

    }
}
