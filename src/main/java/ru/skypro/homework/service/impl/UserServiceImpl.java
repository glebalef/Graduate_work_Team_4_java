package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Метод редактирования пользователя
     *
     * @param userDto data transfer object пользователя
     * @param email email пользователя
     * @return объект userDto
     */
    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        logger.info("Was invoked method for editing user: {}", userDto);

        userDto.setEmail(email);
        UserInfo userFound = userRepository.findByEmail(email);
        Avatar avatar = avatarRepository.findAvatarByUserInfoId(userFound.getId());

        userFound.setEmail(userDto.getEmail());
        userFound.setFirstName(userDto.getFirstName());
        userFound.setLastName(userDto.getLastName());
        userFound.setPhone(userDto.getPhone());
        userFound.setCity(userDto.getCity());
        userRepository.save(userFound);

        return userMapper.usertoUserDto(userFound, avatar);
    }

    /**
     * Метод получения пользователя
     *
     * @param email email пользователя
     * @return объект userDto
     */
    @Override
    public UserDto getUser(String email) {
        logger.info("Was invoked method for getting user");
        UserInfo userFound = userRepository.findByEmail(email);
        Avatar avatar = avatarRepository.findAvatarByUserInfoId(userFound.getId());
        return userMapper.usertoUserDto(userFound, avatar);
    }

    /**
     * Метод редактирования аватарки пользователя
     *
     * @param avatarFile картинка для профиля пользователя
     * @param email получение данных пользователя
     */
    @Override
    public void updateUserImage(MultipartFile avatarFile, String email) throws IOException {
        logger.info("Was invoked method for editing user's image");
        UserInfo userInfo = userRepository.findByEmail(email);
        Avatar avatar = avatarRepository.findAvatarByUserInfoId(userInfo.getId());
        //если аватар еще не установлен
        if (avatar == null) {
            avatar = new Avatar();
        }
        avatar.setFilePath("/users/" + userInfo.getId() + "/image");
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatar.setUserInfo(userInfo);
        avatarRepository.save(avatar);
    }
    /**
     * Метод получения аватарки пользователя
     *
     * @param userId id пользователя
     * @return данные аватарки пользователя
     */
    @Override
    public byte[] getImage(Long userId) {
        logger.info("Was invoked method for getting avatar");
        Avatar avatar = avatarRepository.findAvatarByUserInfoId(userId);
        return avatar.getData();
    }
}
