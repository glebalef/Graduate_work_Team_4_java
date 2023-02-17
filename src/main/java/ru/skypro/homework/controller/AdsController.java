package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.impl.CommentServiceImpl;
import ru.skypro.homework.impl.ImageServiceImpl;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    private final CommentServiceImpl commentService;
    private final AdsService adsService;
    private final ImageServiceImpl imageService;

    AdsController(CommentServiceImpl commentService, AdsService adsService, ImageServiceImpl imageService,
                  AdsRepository adsRepository,
                  AvatarRepository avatarRepository,
                  ImageRepository imageRepository) {
        this.commentService = commentService;
        this.adsService = adsService;
        this.imageService = imageService;
    }

    // /ads
    @GetMapping("")
    public AdsDto getAds() {
        return new AdsDto();
    }

    @PostMapping( value = ""
            //consumes = {
            //MediaType.APPLICATION_JSON_VALUE,
            //MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    public AdsDto addAds(@RequestBody(required = true) CreateAdsDto properties)
            throws IOException {

        adsService.addAds(properties);
        Long id = adsService.addAds(properties).getPk();
                //imageService.uploadImage(id, image);
        return  adsService.getAds(id);
    }

    // /ads/{id}
    @GetMapping("{id}")
    public FullAdsDto getFullAd(@PathVariable Long id) {
        return adsService.getFullAds(id);
    }

    @DeleteMapping("{id}")
    public FullAdsDto removeAds(@PathVariable Long id) {
        return adsService.removeFullAds(id);
    }

    @PatchMapping("{id}")
    public AdsDto updateAds(@PathVariable Long id, @RequestBody CreateAdsDto ads) {
        return adsService.updateAds(id, ads);
    }


    // {ad_pk}/comments/{id}
    @GetMapping("{adPk}/comments/{id}")
    public ResponseEntity <CommentDto> getComments(@PathVariable Long adPk, @PathVariable Long id) {
        CommentDto commentDto = commentService.getComments(id);
        if (commentDto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("{adPk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long adPk,
                                         @PathVariable Long id) {
        commentService.deleteComments(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping ("{adPk}/comments/{id}")
    public ResponseEntity <CommentDto> updateComments(@PathVariable Long adPk,
                                    @PathVariable Long id,
                                     @RequestBody CommentDto comment) {
        CommentDto commentDto = commentService.updateComments(adPk, id, comment);
        if (commentDto == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("me")
    public ResponseWrapperAdsDto getAdsMe() {
        return new ResponseWrapperAdsDto();
    }
}

