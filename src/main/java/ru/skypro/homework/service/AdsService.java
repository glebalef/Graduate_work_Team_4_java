package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;

import java.io.IOException;
import java.util.List;

public interface AdsService {
    public AdsDto getAds(Long pk);
    public AdsDto addAds(CreateAdsDto createAdsDto);
    public FullAdsDto getFullAds(Long pk);
    public FullAdsDto removeFullAds(Long pk);
    public AdsDto updateAds(Long pk, CreateAdsDto createAdsDto);
    public ResponseWrapperAdsDto getAllAds (Long id);
    public ResponseWrapperAdsDto getAll ();
    public Ads getAdsNotDtoById (Long id);
    public ResponseWrapperAdsDto searchAds(String part);
}
