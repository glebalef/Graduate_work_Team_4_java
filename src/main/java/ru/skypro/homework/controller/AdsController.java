package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import java.io.IOException;
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

    @GetMapping("")
    @Operation(tags = {"Объявления"},
            operationId = "getALLAds",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAdsDto.class))
                    })
            })
    public ResponseEntity<ResponseWrapperAdsDto> getALLAds() {
        return ResponseEntity.ok(adsService.getAll());
    }


    @PostMapping(value = "",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    @Operation(tags = {"Объявления"},
            summary = "addAds",
            operationId = "addAds",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            })
    public ResponseEntity<AdsDto> addAds(@RequestPart CreateAdsDto properties,
                                         @RequestPart MultipartFile image,
                                         Authentication authentication)
            throws IOException {
        // создаем сущность Ads
        Long id = adsService.addAds(properties, authentication).
                getPk(); // сохранили в базу, вытащили id

        // сохраняем сущность Image
        imageService.uploadImage(id, image);

        // добавили картинку к Ads
        List<Image> images = new ArrayList<>();
        images.add(imageService.findImage(id));
        Ads reWrite = Objects.requireNonNull(adsService.getAdsNotDtoById(id));
        reWrite.setImage(images);
        adsService.justSaveAds(reWrite);
        adsService.getAds(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // /ads/{id}
    @GetMapping("{id}")
    @Operation(tags = {"Объявления"},
            summary = "getFullAdd",
            operationId = "getAds",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = FullAdsDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<FullAdsDto> getAds(@PathVariable Long id) {
        return ResponseEntity.ok(adsService.getFullAds(id));
    }

    @DeleteMapping("{id}")
    @Operation(tags = {"Объявления"},
            summary = "removeAds",
            operationId = "removeAds",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            })
    public ResponseEntity<AdsDto> removeAds(@PathVariable Long id, Authentication authentication) {
        adsService.removeFullAds(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{id}")
    @Operation(tags = {"Объявления"},
            summary = "updateAds",
            operationId = "updateAds",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<AdsDto> updateAds(@PathVariable Long id, @RequestBody CreateAdsDto ads, Authentication authentication) {
        return ResponseEntity.ok(adsService.updateAds(id, ads, authentication));
    }

    // Комментарии (Comments)

    @PostMapping("{adPk}/comments")
    @Operation(tags = {"Объявления"},
            summary = "addComments",
            operationId = "addComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<CommentDto> addComments(@PathVariable Long adPk,
                                                  @RequestBody CommentDto commentDto,
                                                  Authentication authentication) {
        commentDto.setCreatedAt(LocalDateTime.now().toString());
        return ResponseEntity.ok(commentService.addComments(commentDto, adPk, authentication));
    }

    @GetMapping("{adPk}/comments")
    @Operation(tags = {"Объявления"},
            summary = "getComments",
            operationId = "getComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperComment.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Long adPk) {
        return ResponseEntity.ok(commentService.getAll(adPk));
    }

    @GetMapping("{adPk}/comments/{id}")
    @Operation(tags = {"Объявления"},
            summary = "getComments",
            operationId = "getComments_1",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<CommentDto> getComments(@PathVariable Long adPk, @PathVariable Long id) {
        CommentDto commentDto = commentService.getComments(id);
        if (commentDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("{ad_Pk}/comments/{id}")
    @Operation(tags = {"Объявления"},
            summary = "deleteComments",
            operationId = "deleteComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<Void> deleteComments(@PathVariable Long ad_Pk,
                                               @PathVariable Long id,
                                               Authentication authentication) {
        commentService.deleteComments(authentication, ad_Pk, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{ad_Pk}/comments/{id}")
    @Operation(tags = {"Объявления"},
            summary = "updateComments",
            operationId = "updateComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
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
    @Operation(tags = {"Объявления"},
            operationId = "getAdsMeUsingGET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adsService.getAllAds(authentication));
    }

}
