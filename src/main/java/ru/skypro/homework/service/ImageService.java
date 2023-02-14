package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService  {

    void uploadImage (Long adsId, MultipartFile avatarFile) throws IOException;
}
