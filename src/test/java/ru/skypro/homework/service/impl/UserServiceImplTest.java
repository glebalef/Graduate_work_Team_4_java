package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private UserInfo user;
    private Avatar avatar;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new UserInfo();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setPhone("+1234567890");
        user.setCity("LA");

        userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setCity(user.getCity());

        avatar = new Avatar();
        avatar.setUserInfo(user);
        avatar.setFilePath("/users/1/image");
        avatar.setFileSize(1L);
        avatar.setMediaType("image/png");
    }

    @Test
    void updateUser_shouldUpdateUserAndReturnUserDto() {
        // Arrange
        UserDto userDto2 = new UserDto();
        userDto2.setEmail(user.getEmail());
        userDto2.setFirstName("anotherName");
        userDto2.setLastName("anotherLastName");
        userDto2.setPhone("+0987654321");
        userDto2.setCity("NY");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(avatarRepository.findAvatarByUserInfoId(user.getId())).thenReturn(avatar);
        when(userMapper.userToUserDto(user, avatar)).thenReturn(userDto2);

        UserDto updatedUser = service.updateUser(userDto2, user.getEmail());

        assertEquals(userDto2, updatedUser);
    }


    @Test
    void shouldReturnUserDto() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(avatarRepository.findAvatarByUserInfoId(user.getId())).thenReturn(avatar);
        when(userMapper.userToUserDto(user, avatar)).thenReturn(userDto);

        UserDto getUser = service.getUser(user.getEmail());
        assertEquals(userDto, getUser);
    }

    @Test
    void updateUserImage() throws IOException {

        MockMultipartFile image = new MockMultipartFile(
                "image.jpg",
                "image.jpg",
                "image/jpeg",
                "image content".getBytes());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(avatarRepository.findAvatarByUserInfoId(user.getId())).thenReturn(avatar);

        service.updateUserImage(image, user.getEmail());
        assertEquals(image.getContentType(), avatar.getMediaType());
        assertEquals(image.getBytes().length, avatar.getFileSize());
        assertEquals(image.getBytes(), avatar.getData());
    }

    @Test
    void getImage() {
        userDto.setImage(avatar.getFilePath());

        when(avatarRepository.findAvatarByUserInfoId(user.getId())).thenReturn(avatar);

        service.getImage(user.getId());
        assertEquals(avatar.getFilePath(), userDto.getImage());
    }
}