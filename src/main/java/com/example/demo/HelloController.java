package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/test")
    public String test() {
        return "Test API is working!";
    }
}
