package com.epam.heroku.shmeroku.controller;

import com.epam.heroku.shmeroku.model.User;
import com.epam.heroku.shmeroku.model.UserDto;
import com.epam.heroku.shmeroku.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class HelloController {
    private final UserRepository userRepository;

    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String main() {
        return "Hello from server!";
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @PostMapping("/register")
    public User reg(UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getPassword());
        return userRepository.save(user);
    }
}
