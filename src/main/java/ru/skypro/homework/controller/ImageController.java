//package ru.skypro.homework.controller;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import ru.skypro.homework.entity.Image;
//import ru.skypro.homework.repository.ImageRepository;
//import ru.skypro.homework.service.ImageService;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequestMapping("/image")
//@CrossOrigin(value = "http://localhost:3000")
//@RestController
//public class ImageController {
//
//    private final ImageService imageService;
//    private final ImageRepository imageRepository;
//
//    public ImageController(ImageService imageService, ImageRepository imageRepository) {
//        this.imageService = imageService;
//        this.imageRepository = imageRepository;
//    }
//
//    @PostMapping( value = "", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
//    public List<String> updateImage (@RequestParam int id,
//                                     @RequestParam MultipartFile pic) throws IOException {
//        Image image = new Image();
//        image.setData(pic.getBytes());
//        image.setFilePath(pic.getOriginalFilename());
//        image.setMediaType(pic.getContentType());
//        imageRepository.save(image);
//
//        return new ArrayList<String>();
//    }
//}
