package com.kiran.expenseagain.controller;

import com.kiran.expenseagain.entity.User;
import com.kiran.expenseagain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(Pageable page) {
        List<User> allUsers = userService.getAllUsers(page).toList();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
