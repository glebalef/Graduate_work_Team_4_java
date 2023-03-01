package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    public ImageServiceImpl(AdsRepository adsRepository, ImageRepository imageRepository) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
    }

    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;
   // @Value("${path.to.images.folder}")
    private String imagesDir = "C:\\Users\\gleba\\Documents\\Graduate java project\\Images";

    @Override
    public void uploadImage(Long adsId, MultipartFile imageFile) throws IOException {
        logger.info("Invoke method uploadImage");
        Ads ads = adsRepository.findById(adsId).orElse(null);
        Path filePath = Path.of(imagesDir, adsId + "." + getExtensions(Objects.requireNonNull(imageFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Image image = imageRepository.findById(adsId).orElseGet(Image::new);
        image.setAds(ads);
        image.setFilePath("/image/"+adsId+"/");
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        imageRepository.save(image);

    }

    private String getExtensions(String fileName) {
        logger.info("Invoke method getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Image findImage(Long adsPk) {
        logger.info("Invoke method findImage");
        return imageRepository.findImageByAds_pk(adsPk);
    }


    public List<String> findFilePathsByAdsPk(Long adsPk) {
        logger.info("Invoke method findFilePathsByAdsPk");
        List<String> list = new ArrayList<>();
        for (Image value : imageRepository.findImagesByAds_Pk(adsPk)) {
            list.add(value.getFilePath());
        }
        return list;
    }

    public void imageReWrite(Long id) {
        logger.info("Invoke method imageReWrite");
        List<Image> images = new ArrayList<Image>();
        Ads reWrite = Objects.requireNonNull(adsRepository.findById(id).orElse(null));
        reWrite.setImage(images);
        adsRepository.save(reWrite);
    }


    @Override
    public void updateImage (Long adsId, MultipartFile imageFile) throws IOException {
        logger.info("Invoke method updateImage");
        Image image = imageRepository.findImageByAds_pk(adsId);
        assert image != null;
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        imageRepository.save(image);
    }
}
