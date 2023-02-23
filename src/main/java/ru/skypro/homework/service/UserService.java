package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;


public interface UserService {

    UserDto updateUser(UserDto userDto);

    UserDto getUser(String email);
    void updateUserImage(String image);
}
