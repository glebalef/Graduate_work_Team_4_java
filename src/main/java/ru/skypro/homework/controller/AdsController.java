package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.impl.CommentServiceImpl;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    private final CommentServiceImpl commentService;

    AdsController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

// /ads
    @GetMapping("")
    public AdsDto getAds() {
        return new AdsDto();
    }
    @PostMapping("")
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

