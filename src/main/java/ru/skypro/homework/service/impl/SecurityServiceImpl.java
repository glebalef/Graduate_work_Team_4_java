package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.CommentMapper;
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

    @Override
    public boolean accessAds(Authentication authentication, Long adsId) {
        logger.info("Invoke method accessAds");
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        Long userAdsId = ads.getUserInfo().getId();
        Long userInfoId = userRepository.findByEmail(authentication.getName()).getId();
        return userInfoId.equals(userAdsId) || role(authentication);
    }
    @Override
    public boolean accessComments(Authentication authentication, Long commentId) {
        logger.info("Invoke method accessComments");
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Long userIdComment = comment.getUserInfo().getId();
        Long userInfoId = userRepository.findByEmail(authentication.getName()).getId();
        return userInfoId.equals(userIdComment) || role(authentication);
    }

    @Override
    public boolean role(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

    }

}
