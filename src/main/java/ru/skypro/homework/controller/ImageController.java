package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    @PostMapping(value = "{id}")
    public List<String> updateImage (@PathVariable int id,
                                     @RequestParam MultipartFile image) {
        return new ArrayList<String>();
    }
}
