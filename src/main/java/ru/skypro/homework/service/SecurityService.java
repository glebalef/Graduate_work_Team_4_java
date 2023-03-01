package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    boolean accessAds(Authentication authentication, Long adsId);
    boolean accessComments(Authentication authentication, Long commentId);
    boolean role(Authentication authentication);
}
