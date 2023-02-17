package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

public interface AdsService {
    public AdsDto getAds(Long pk);
    public AdsDto addAds(CreateAdsDto createAdsDto);
    public FullAdsDto getFullAds(Long pk);
    public FullAdsDto removeFullAds(Long pk);
    public AdsDto updateAds(Long pk, CreateAdsDto createAdsDto);
}
