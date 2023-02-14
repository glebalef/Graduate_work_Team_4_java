package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.impl.AdsServiceImpl;
import ru.skypro.homework.impl.CommentServiceImpl;
import ru.skypro.homework.impl.ImageServiceImpl;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.ImageRepository;

import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    private final CommentServiceImpl commentService;
    private final AdsServiceImpl adsService;
    private final ImageServiceImpl imageService;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final AdsMapper adsMapper;
    private final AdsRepository adsRepository;
    private final AvatarRepository avatarRepository;
    private final ImageRepository imageRepository;

    AdsController(CommentServiceImpl commentService, AdsServiceImpl adsService, ImageServiceImpl imageService, CreateAdsDtoMapper createAdsDtoMapper, AdsMapper adsMapper,
                  AdsRepository adsRepository,
                  AvatarRepository avatarRepository,
                  ImageRepository imageRepository) {
        this.commentService = commentService;
        this.adsService = adsService;
        this.imageService = imageService;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.adsMapper = adsMapper;
        this.adsRepository = adsRepository;
        this.avatarRepository = avatarRepository;
        this.imageRepository = imageRepository;
    }

    // /ads
    @GetMapping("")
    public AdsDto getAds() {
        return new AdsDto();
    }

    @PostMapping( value = "", consumes = {"application/json", MediaType.MULTIPART_FORM_DATA_VALUE})
    public AdsDto addAds(@RequestPart(required = false) CreateAdsDto properties,
                         @RequestPart(required = false) MultipartFile image)
            throws IOException {
        adsService.addAds(properties);
        imageService.uploadImage(1L, image);
        return adsMapper.adsToAdsDto(createAdsDtoMapper.createAdsDtoToAds(properties));
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
    public FullAdsDto updateAds(@PathVariable Long id, @RequestBody CreateAdsDto ads) {
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

