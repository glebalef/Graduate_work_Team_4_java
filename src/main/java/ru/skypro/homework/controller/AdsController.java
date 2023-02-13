package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

// /ads
    @GetMapping("")
    public AdsDto getAds() {
        return new AdsDto();
    }
    @PostMapping(consumes = {"multipart/form-data"})
    public AdsDto addAds(@RequestPart CreateAdsDto properties,
                         @RequestPart MultipartFile image) {
        return new AdsDto();
    }

    // /ads/{id}
    @GetMapping("{id}")
    public FullAdsDto getFullAd(@PathVariable int id) {
        return new FullAdsDto();
    }

    @DeleteMapping("{id}")
    public FullAdsDto removeAds(@PathVariable int id) { return new FullAdsDto();
    }

    @PatchMapping("{id}")
    public FullAdsDto updateAds(@PathVariable int id, @RequestBody CreateAdsDto ads) { return new FullAdsDto();
    }


    // {ad_pk}/comments/{id}
    @GetMapping("{adPk}/comments/{id}")
    public CommentDto getComments(@PathVariable String adPk,
                                  @PathVariable String id) {
        return new CommentDto();
    }

    @DeleteMapping("{adPk}/comments/{id}")
    public CommentDto deleteComments(@PathVariable String adPk,
                                     @PathVariable String id) {
        return new CommentDto();
    }

    @PatchMapping ("{adPk}/comments/{id}")
    public CommentDto updateComments(@PathVariable String adPk,
                                    @PathVariable String id,
                                     @RequestBody CommentDto comment) {
        return new CommentDto();
    }

    @GetMapping("me")
    public ResponseWrapperAdsDto getAdsMe() {
        return new ResponseWrapperAdsDto();
    }
}

