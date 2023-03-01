package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

public interface ImageService {

    void uploadImage(Long adsId, MultipartFile avatarFile) throws IOException;
    public void updateImage (Long adsId, MultipartFile imageFile) throws IOException;

    Image findImage(Long adsPk);


}
