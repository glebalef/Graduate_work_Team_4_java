package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;


@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;
    private final AdsService adsService;

    @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editPhotoAdd(
            @RequestParam MultipartFile image,
            @PathVariable Long id, Authentication authentication) throws IOException {
        imageService.updateImage(id, image, authentication);
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable Long id) {
        Ads ads = adsService.getAdsNotDtoById(id);
        return ads.getImage().get(0).getData();
    }
}
