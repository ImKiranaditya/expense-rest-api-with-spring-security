package com.kiran.expenseagain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @NotBlank(message = "Name should not be null or empty")
    @Size(min = 3)
    private String username;

    @NotBlank(message = "Email should not be null or empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password should not be null or empty")
    @Size(min = 5, message = "Password must be at least 5 character")
    private String password;

    private Long age = 0L;
}
