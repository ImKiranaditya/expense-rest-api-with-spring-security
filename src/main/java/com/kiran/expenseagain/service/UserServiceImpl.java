package com.kiran.expenseagain.service;

import com.kiran.expenseagain.entity.User;
import com.kiran.expenseagain.entity.UserModel;
import com.kiran.expenseagain.exception.ItemAlreadyExistException;
import com.kiran.expenseagain.exception.ResourceNotFoundException;
import com.kiran.expenseagain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder bcryptPasswordEncoder;

    @Override
    public User getLoggedInUser() {
        log.info("UserServiceImpl :: updateUser :: getLoggedInUser");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with the email :" + email));
        return user;
    }

    @Override
    public User saveUser(UserModel userModal) {
        log.info("UserServiceImpl :: updateUser :: getLoggedInUser");
        if (repository.existsByEmail(userModal.getEmail())) {
            throw new ItemAlreadyExistException("User is already registered with the email :" + userModal.getEmail());
        }
        User user = new User();
        BeanUtils.copyProperties(userModal, user);
        user.setPassword(bcryptPasswordEncoder.encode(userModal.getPassword()));
        return repository.save(user);
    }

    @Override
    public User readUser() {
        log.info("UserServiceImpl :: getUserById called");
        Optional<User> optionalUser = repository.findById(getLoggedInUser().getId());
        log.info("UserServiceImpl :: getUserById :: optionalUser: " + optionalUser);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("User with the id " + getLoggedInUser().getId() + " is not found");
        }
    }

    public Page<User> getAllUsers(Pageable page) {
        log.info("UserServiceImpl :: getAllUsers called");
        return repository.findAll(page);
    }

    public void deleteUser() {
        log.info("UserServiceImpl :: deleteUser called");
        User user = readUser();
        repository.delete(user);
    }

    public User updateUser(UserModel user) {
        log.info("UserServiceImpl :: updateUser called");
        User userById = readUser();
        log.info("UserServiceImpl :: updateUser :: userById: " + userById);
        if (userById != null) {
            userById.setUsername(user.getUsername() != null ? user.getUsername() : userById.getUsername());
            userById.setEmail(user.getEmail() != null ? user.getEmail() : userById.getEmail());
            userById.setPassword(user.getPassword() != null ? bcryptPasswordEncoder.encode(user.getPassword()) : userById.getPassword());
            userById.setAge(user.getAge() != null ? user.getAge() : userById.getAge());
            return repository.save(userById);
        } else {
            throw new ResourceNotFoundException("User is not found");
        }
    }
}
