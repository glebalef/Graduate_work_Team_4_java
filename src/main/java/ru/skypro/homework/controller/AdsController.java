package ru.skypro.homework.controller;

import liquibase.pro.packaged.S;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.impl.CommentServiceImpl;
import ru.skypro.homework.impl.ImageServiceImpl;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.OctetStreamData;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    private final CommentServiceImpl commentService;
    private final AdsService adsService;
    private final ImageServiceImpl imageService;
    private final AdsRepository adsRepository;

    AdsController(CommentServiceImpl commentService, AdsService adsService, ImageServiceImpl imageService,
                  AdsRepository adsRepository) {
        this.commentService = commentService;
        this.adsService = adsService;
        this.imageService = imageService;
        this.adsRepository = adsRepository;
    }

    // /ads
    @GetMapping("")
    public ResponseWrapperAdsDto getALLAds() {
        Long id = 1L; //пока так
        return adsService.getAll();
    }

    @PostMapping(value = "",
            consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public AdsDto addAds(@RequestPart(required = true) CreateAdsDto properties,
                         @RequestPart MultipartFile image)
            throws IOException {

        adsService.addAds(properties);
        Long id = adsService.addAds(properties).getPk();
        imageService.uploadImage(id, image);
        return adsService.getAds(id);
    }

    // /ads/{id}
    @GetMapping("{id}")
    public FullAdsDto getAds(@PathVariable Long id) {
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
    public ResponseEntity<CommentDto> getComments(@PathVariable Long adPk, @PathVariable Long id) {
        CommentDto commentDto = commentService.getComments(id);
        if (commentDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("{adPk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long adPk,
                                               @PathVariable Long id) {
        commentService.deleteComments(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{adPk}/comments/{id}")
    public ResponseEntity<CommentDto> updateComments(@PathVariable Long adPk,
                                                     @PathVariable Long id,
                                                     @RequestBody CommentDto comment) {
        CommentDto commentDto = commentService.updateComments(adPk, id, comment);
        if (commentDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("me")
    public ResponseWrapperAdsDto getAdsMe() {
        return new ResponseWrapperAdsDto();
    }

    @PostMapping(value = "{adsPk}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity <String> uploadImage (@PathVariable Long adsPk, @RequestParam MultipartFile image) throws IOException {
        imageService.uploadImage(adsPk, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping (value = "/{adsPk}/image")
    public void downloadImage (@PathVariable Long adsPk, HttpServletResponse response) throws IOException {
        Image image = imageService.findImage(adsPk);

        Path path = Path.of(image.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {

            response.setStatus(200);
            response.setContentType(image.getMediaType());
            response.setContentLength((int)image.getFileSize());
            is.transferTo(os);
        }
    }
}

