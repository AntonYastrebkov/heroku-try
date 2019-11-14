package com.epam.heroku.shmeroku.controller;

import com.epam.heroku.shmeroku.model.Role;
import com.epam.heroku.shmeroku.model.User;
import com.epam.heroku.shmeroku.model.UserDto;
import com.epam.heroku.shmeroku.repository.UserRepository;
import com.epam.security.service.SecurityService;
import com.epam.security.service.SecurityService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    private final UserRepository userRepository;
    private final SecurityService service;

    public HelloController(UserRepository userRepository, SecurityService service) {
        this.userRepository = userRepository;
        this.service = service;
    }

    @GetMapping("/")
    public String main() {
        return "Hello from server!";
    }

    @GetMapping("/user/{id}/admin")
    public String admin(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (service.isAdmin(user.getRole().toString())) {
            return "Very secret content";
        } else {
            return "GO AWAY!!!";
        }
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @PostMapping("/admin/make")
    public User makeAdmin(
            @RequestParam Long userId,
            @RequestParam String password
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if ("secret".equals(password)) {
            user.setRole(Role.ADMIN_USER);
            return userRepository.save(user);
        }
        return user;
    }

    @PostMapping("/register")
    public User reg(UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getPassword(), Role.USER);
        return userRepository.save(user);
    }
}
