/*package ru.skypro.homework.service.impl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.byteThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
@Mock
    UserRepository userRepository;
    @Mock
    AvatarRepository avatarRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl service;
    final UserInfo userInfo = new UserInfo("user@gmail.com", "John", 1L, "Smith", "123", "01.01.2023", "NewYork", "password");
    final UserDto userDto1 = new UserDto();
    final UserDto userDto2 = new UserDto();


    @Test
    void updateUser() {
        Avatar avatar = new Avatar(1L, "path", 10L, "type", null, userInfo);

        userDto1.setId(12L);
        userDto1.setEmail("email");
        userDto1.setFirstName("firstName");
        userDto1.setImage(avatar.getFilePath());

        userDto2.setId(12L);
        userDto2.setEmail("email");
        userDto2.setFirstName("anotherName");
        userDto2.setImage(avatar.getFilePath());
        when(service.updateUser(userDto1, "email")).thenReturn(userDto2);
        assertEquals(userDto1.getFirstName(), "anotherName");
    }

    @Test
    void getUser() {
        userDto2.setId(12L);
        userDto2.setEmail("email");
        userDto2.setFirstName("anotherName");

        when(service.getUser("email")).thenReturn(userDto2);
        assertEquals(userDto1.getFirstName(), "anotherName");
    }

    @Test
    void updateUserImage() {
    }

    @Test
    void getImage() {
    }
}*/