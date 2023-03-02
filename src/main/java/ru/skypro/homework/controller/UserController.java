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
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;



    @PostMapping("/set_password")
    @Operation(tags = {"Пользователи"},
            summary = "setPassword",
            operationId = "setPassword",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = NewPasswordDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            })
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto,
                                                      Authentication authentication) {

        if (authService.changePassword(newPasswordDto, authentication.getName())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/me")
    @Operation(tags = {"Пользователи"},
            summary = "updateUser",
            operationId = "updateUser",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = UserInfo.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            })
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(userDto, authentication.getName()));
    }

    @GetMapping("/me")
    @Operation(tags = {"Пользователи"},
            summary = "getUser",
            operationId = "getUser_1",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = UserInfo.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            })
    public ResponseEntity<UserDto> getUser_1(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(tags = {"Пользователи"},
            description = "updateUserImage",
            summary = "updateUserImage",
            operationId = "updateUserImage",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = UserInfo.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            })
    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image, Authentication authentication) throws IOException {
        userService.updateUserImage(image, authentication.getName());
        return ResponseEntity.ok().build();
    }


    //    @GetMapping(value = "/me/image/{id}/", produces = {MediaType.IMAGE_PNG_VALUE})
//    public byte[] getAvatrar(@PathVariable Long id) {
//     UserDto user = userService.ge;
//        return user.getImage();
//    }
    @GetMapping(value = "/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable("id") Long id) throws IOException {
        return userService.getImage(id);
    }
}
