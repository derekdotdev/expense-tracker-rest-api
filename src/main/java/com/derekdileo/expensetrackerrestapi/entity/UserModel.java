package com.derekdileo.expensetrackerrestapi.entity;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;

@Data
public class UserModel {

    private String name;

    private String email;

    private String password;

    private Date dob = new Date(0);

}
