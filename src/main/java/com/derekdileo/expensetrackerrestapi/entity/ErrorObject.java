package com.derekdileo.expensetrackerrestapi.entity;

import lombok.Data;

import java.util.Date;

/*
* Model Class for custom exceptions.
* Uses Lombok @Data annotation to create:
* getters, setters, toString, hashCode, equals
*/

@Data
public class ErrorObject {

    private Integer statusCode;

    private String message;

    private Date timestamp;
}
