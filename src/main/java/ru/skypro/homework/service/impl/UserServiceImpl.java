package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public NewPasswordDto setPassword(NewPasswordDto newPasswordDto) {
        logger.debug("Was invoked method for setting new password: {}", newPasswordDto);
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        logger.debug("Was invoked method for editing user: {}", userDto);

        Optional<User> foundUser = userRepository.findById(userDto.getId());
        if (foundUser.isPresent()) {
            User userFound = foundUser.get();
            userFound.setEmail(userDto.getEmail());
            userFound.setFirstName(userDto.getFirstName());
            userFound.setLastName(userDto.getLastName());
            userFound.setPhone(userDto.getPhone());
            userFound.setCity(userDto.getCity());
            userRepository.save(userFound);
            return UserMapper.INSTANCE.userToUserDto(userFound);
        }
        return null;
    }

    @Override
    public UserDto getUser() {
        logger.debug("Was invoked method for getting user");
        return null;
    }

    @Override
    public void updateUserImage(String image) {
        logger.debug("Was invoked method for editing user's image");

    }
}
