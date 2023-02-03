package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    @GetMapping("{id}")
    public FullAdsDto getFullAd(@PathVariable int id) {
        return new FullAdsDto();
    }

    @DeleteMapping("{id}/delete")
    public FullAdsDto removeAds(@PathVariable int id) {
        return new FullAdsDto();
    }

    @PostMapping("")
    public AdsDto updateAds(@RequestBody CreateAdsDto createAds) {
        return new AdsDto();
    }

    @GetMapping("{ad_pk}/comments/{id}")
    public CommentDto getComments(@PathVariable String ad_pk,
                                  @PathVariable String id) {
        return new CommentDto();
    }

    @DeleteMapping("{ad_pk}/comments/{id}/delete")
    public CommentDto deleteComments(@PathVariable String ad_pk,
                                     @PathVariable String id) {
        return new CommentDto();
    }

    @PostMapping("comments")
    public CommentDto deleteComments(@RequestBody CommentDto commentDto) {
        return new CommentDto();
    }

    @PostMapping("me")
    public ResponseWrapperAdsDto getAdsMe() {
        return new ResponseWrapperAdsDto();
    }
}

