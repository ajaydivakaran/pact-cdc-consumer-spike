package me.spike.consumer.web.controller;

import me.spike.consumer.web.contract.Greet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greet")
    public Greet greet() {
        return new Greet("hello world");
    }
}
