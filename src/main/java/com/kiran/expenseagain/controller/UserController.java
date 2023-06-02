package com.kiran.expenseagain.controller;

import com.kiran.expenseagain.entity.User;
import com.kiran.expenseagain.entity.UserModel;
import com.kiran.expenseagain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @DeleteMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>("User is deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/profile")
    ResponseEntity<User> getUserById() {
        User user = userService.readUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/profile")
    ResponseEntity<User> updateUser(@RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.updateUser(userModel), HttpStatus.OK);
    }

}
