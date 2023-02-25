package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserDetailsManager manager, UserRepository userRepository) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        logger.info("Invoke method login");
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        logger.info("Invoke method register");
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );

        UserInfo userInfo = UserMapper.INSTANCE.registerReqToUser(registerReq);
        userInfo.setRegDate(LocalDate.now().toString());
        userRepository.save(userInfo);

        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDto newPasswordDto, String email) {
        logger.info("Invoke method changePassword");
        UserInfo userInfo = userRepository.findByEmail(email);
        String oldPassword = newPasswordDto.getCurrentPassword();
        String newPassword = newPasswordDto.getNewPassword();
        if (oldPassword.equals(userInfo.getPassword()) || encoder.matches(newPassword, userInfo.getPassword())) {
            manager.changePassword(oldPassword, "{bcrypt}" + encoder.encode(newPassword));
            userInfo.setPassword(newPassword);
            userRepository.save(userInfo);
            return true;
        }
        return false;
    }

}