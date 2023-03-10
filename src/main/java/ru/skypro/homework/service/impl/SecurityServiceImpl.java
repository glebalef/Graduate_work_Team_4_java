package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.SecurityService;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    /**
     * Метод получения объявления аутентифицированному пользователю
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * Используется метод репозитория {@link ru.skypro.homework.repository.UserRepository#findByEmail(String)}
     *
     * @param authentication аутентификация пользователя
     * @param adsId          идентификатор объявления
     * @return полученный доступ аутентифицированному пользователю с определенной ролью
     */
    @Override
    public boolean accessAds(Authentication authentication, Long adsId) {
        logger.info("Invoke method accessAds");
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        Long userAdsId = ads.getUserInfo().getId();
        Long userInfoId = userRepository.findByEmail(authentication.getName()).getId();
        return userInfoId.equals(userAdsId) || role(authentication);
    }

    /**
     * Метод получения комментарев аутентифицированному пользователю
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * Используется метод репозитория {@link ru.skypro.homework.repository.UserRepository#findByEmail(String)}
     *
     * @param authentication аутентификация пользователя
     * @param commentId      идентификатор комментария
     * @return полученный доступ аутентифицированному пользователю с определенной ролью
     */
    @Override
    public boolean accessComments(Authentication authentication, Long commentId) {
        logger.info("Invoke method accessComments");
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Long userIdComment = comment.getUserInfo().getId();
        Long userInfoId = userRepository.findByEmail(authentication.getName()).getId();
        return userInfoId.equals(userIdComment) || role(authentication);
    }

    /**
     * Метод получения доступа для админа
     * <br>
     *
     * @param authentication аутентификация пользователя
     * @return полученный доступ админу
     */
    @Override
    public boolean role(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

    }

}
