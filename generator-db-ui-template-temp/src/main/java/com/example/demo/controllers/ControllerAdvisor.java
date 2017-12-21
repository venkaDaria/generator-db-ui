package com.example.demo.controllers;

import org.hibernate.PersistentObjectException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(PersistentObjectException.class)
    public String handle(final Exception ex) {
        return "";
    }
}