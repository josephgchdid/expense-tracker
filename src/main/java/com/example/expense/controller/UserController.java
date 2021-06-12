package com.example.expense.controller;

import com.example.expense.model.User;
import com.example.expense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    public static class UserRequest{

        private String userEmail;

        private String userPassword;

        public UserRequest(){}

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }
    }

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUpUser(@NonNull @RequestBody User user){
        return userService.signUpUser(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@NonNull @RequestBody UserRequest userRequest){
        return userService.loginUser(userRequest.getUserEmail(), userRequest.getUserPassword());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id){
        return userService.findUserById(id);
    }
}
