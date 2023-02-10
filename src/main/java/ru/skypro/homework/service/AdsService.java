package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;

public interface AdsService {
    public AdsDto getAds(AdsDto adsDto);
    public AdsDto addAds(CreateAdsDto createAdsDto);
    public FullAdsDto getFullAds(FullAdsDto fullAdsDto);
    public FullAdsDto removeFullAds(FullAdsDto fullAdsDto);
    public FullAdsDto updateAds(CreateAdsDto createAdsDto);
}
