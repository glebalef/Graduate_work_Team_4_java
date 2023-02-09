package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;


public interface UserService {
    NewPasswordDto setPassword(NewPasswordDto newPasswordDto);

    UserDto updateUser(UserDto userDto);

    UserDto getUser();
    void updateUserImage(String image);
}
