package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;


@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    private final ImageService imageService;
    private final AdsService adsService;

    public ImageController(ImageService imageService, AdsService adsService) {
        this.imageService = imageService;
        this.adsService = adsService;
    }

    @PatchMapping (value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void editPhotoAdd(
            @RequestParam MultipartFile image,
            @PathVariable Long id) throws IOException {
        imageService.updateImage(id, image);
        Ads ads = adsService.getAdsNotDtoById(id);
    }

    @GetMapping(value = "/image/{id}/", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable Long id) {
        Ads ads = adsService.getAdsNotDtoById(id);
        return ads.getImage().get(0).getData();
    }
}
