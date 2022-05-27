package com.derekdileo.expensetrackerrestapi.entity;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class UserModel {

    @NotBlank(message = "Name should not be empty")
    private String name;

    @Email(message = "Please enter a valid email")
    @NotNull(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be at least 5 characters long.")
    private String password;

    private Date dob = new Date(0);

}
