package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;

public interface AdsService {
    AdsDto getAds(Long pk);

    AdsDto addAds(CreateAdsDto createAdsDto, Authentication authentication);

    FullAdsDto getFullAds(Long pk);

    void removeFullAds(Long pk, Authentication authentication);

    AdsDto updateAds(Long pk, CreateAdsDto createAdsDto, Authentication authentication);

    ResponseWrapperAdsDto getAllAds(Authentication authentication);

    ResponseWrapperAdsDto getAll();

    Ads getAdsNotDtoById(Long id);

    void justSaveAds(Ads ads);

}
