package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    private final ImageService imageService;
    private final AdsService adsService;

    public ImageController(ImageService imageService, AdsService adsService) {
        this.imageService = imageService;
        this.adsService = adsService;
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String updateImage(
            @RequestParam MultipartFile pic,
            @PathVariable Long id) throws IOException {
        imageService.uploadImage(id, pic);

        return pic.getName();
    }

    @GetMapping(value = "/{id}/", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable Long id) {
        Ads ads = adsService.getAdsNotDtoById(id);
        return ads.getImage().get(0).getData();
    }
}
