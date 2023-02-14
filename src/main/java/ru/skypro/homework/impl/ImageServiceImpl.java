package ru.skypro.homework.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    public ImageServiceImpl(AdsRepository adsRepository, AdsServiceImpl adsServiceImpl, ImageRepository imageRepository) {
        this.adsRepository = adsRepository;
        this.adsServiceImpl = adsServiceImpl;
        this.imageRepository = imageRepository;
    }

    AdsRepository adsRepository;
    AdsServiceImpl adsServiceImpl;
    ImageRepository imageRepository;
    @Value("${path.to.images.folder}")
    private String imagesDir;
    @Override
    public void uploadImage(Long adsId, MultipartFile imageFile) throws IOException {
      Ads ads =   adsRepository.findById(adsId).get();
        Path filePath = Path.of(imagesDir, adsId + "." + getExtensions(imageFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Image image = imageRepository.findById(adsId).get();
        image.setAds(ads);
        image.setFilePath(filePath.toString());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        imageRepository.save(image);
    }
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    }
