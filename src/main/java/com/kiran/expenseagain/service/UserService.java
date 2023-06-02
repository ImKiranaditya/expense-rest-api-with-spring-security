package com.kiran.expenseagain.service;

import com.kiran.expenseagain.entity.User;
import com.kiran.expenseagain.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User saveUser(UserModel userModal);

   Page<User> getAllUsers(Pageable page);

    User readUser();

    void deleteUser();

    User updateUser(UserModel userModel);

    User getLoggedInUser();
}
