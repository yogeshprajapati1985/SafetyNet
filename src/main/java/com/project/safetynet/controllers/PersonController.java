package com.project.safetynet.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class PersonController {

    @GetMapping(path = "/hello")
    public String sayHello(){
        return "Hello Yogesh";
    }
}
