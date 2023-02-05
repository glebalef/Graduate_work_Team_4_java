package ru.skypro.homework.controller;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.UserDto;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@RequestBody UserDto user) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody UserDto user) {
        return user;
    }

    @GetMapping("/me")
    public UserDto getUser(@RequestBody UserDto user) {
        return user;
    }

    @PatchMapping("/me/image")
    public String updateUserImage(@RequestBody String image) {
        return image;
    }
}
