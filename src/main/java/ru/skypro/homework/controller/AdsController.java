package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsController {

    private final CommentService commentService;
    private final AdsService adsService;
    private final ImageService imageService;

    // /ads
    @GetMapping("")
    public ResponseWrapperAdsDto getALLAds() {
        return adsService.getAll();
    }


    @PostMapping(value = "",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )

    public AdsDto addAds(@RequestPart CreateAdsDto properties,
                         @RequestPart(required = false) MultipartFile image,
                         Authentication authentication)
            throws IOException {
        // создаем сущность Ads
        Long id = adsService.addAds(properties, authentication).getPk(); // сохранили в базу, вытащили id

        // сохраняем сущность Image
        imageService.uploadImage(id, image);

        // добавили картинку к Ads
        List<Image> images = new ArrayList<>();
        images.add(imageService.findImage(id));
        Ads reWrite = Objects.requireNonNull(adsService.getAdsNotDtoById(id));
        reWrite.setImage(images);
        adsService.justSaveAds(reWrite);

        return adsService.getAds(id);
    }

    // /ads/{id}
    @GetMapping("{id}")
    public FullAdsDto getAds(@PathVariable Long id) {
        return adsService.getFullAds(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdsDto> removeAds(@PathVariable Long id, Authentication authentication) {
        adsService.removeFullAds(id, authentication);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    public AdsDto updateAds(@PathVariable Long id, @RequestBody CreateAdsDto ads, Authentication authentication) {
        return adsService.updateAds(id, ads, authentication);
    }

    // Комментарии (Comments)

    //ok
    @PostMapping("{adPk}/comments")
    public ResponseEntity<CommentDto> addComments(@PathVariable Long adPk,
                                                  @RequestBody CommentDto commentDto,
                                                  Authentication authentication) {

        commentDto.setCreatedAt(LocalDateTime.now().toString());


        return ResponseEntity.ok(commentService.addComments(commentDto, adPk, authentication));
    }

    // ok
    @GetMapping("{adPk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Long adPk) {

        return ResponseEntity.ok(commentService.getAll(adPk));
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

    @DeleteMapping("{ad_Pk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long ad_Pk,
                                               @PathVariable Long id,
                                               Authentication authentication) {
        commentService.deleteComments(authentication, ad_Pk, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{ad_Pk}/comments/{id}")
    public ResponseEntity<CommentDto> updateComments(@PathVariable Long ad_Pk,
                                                     @PathVariable Long id,
                                                     @RequestBody CommentDto comment,
                                                     Authentication authentication) {
        CommentDto commentDto = commentService.updateComments(ad_Pk, id, comment, authentication);
        if (commentDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication) {
        adsService.getAllAds(authentication);
        return ResponseEntity.ok(adsService.getAllAds(authentication));
    }

    @PostMapping(value = "{adsPk}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@PathVariable Long adsPk, @RequestParam MultipartFile image) throws IOException {
        imageService.uploadImage(adsPk, image);
        return ResponseEntity.ok().build();
    }


    @PostMapping
    public void updateAdsImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Image image = imageService.findImage(id);

        Path path = Path.of(image.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {

            response.setStatus(200);
            response.setContentType(image.getMediaType());
            response.setContentLength((int) image.getFileSize());
            is.transferTo(os);
        }
    }

    // метод поиска для тренировки
    @GetMapping("/search")
    public ResponseWrapperAdsDto searchAds(@RequestParam String search) {
        return adsService.searchAds(search);
    }
}