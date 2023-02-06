package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    @PostMapping("{id}")
    public String[] updateImage (@PathVariable int id,
                                 @RequestBody MultipartFile image) {
        return new String[3];
    }
}
