package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;


public interface UserService {

    UserDto updateUser(UserDto userDto, String email);

    UserDto getUser(String email);

    void updateUserImage(MultipartFile avatarFile, String email) throws IOException;

    byte[] getImage(Long userId) throws IOException;

}
