package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

public interface ImageService {

    void uploadImage(Long adsId, MultipartFile avatarFile) throws IOException;

    Image findImage(Long adsPk);


}
