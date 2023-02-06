package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    @PostMapping("{id}")
    public String[] updateImage (@PathVariable int id) {

        return new String[3];
    }
}
