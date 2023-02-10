package ru.skypro.homework.impl;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.mapper.FullAdsDtoMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

public class AdsServiceImpl implements AdsService {

private final AdsRepository adsRepository;
private final AdsMapper adsMapper;
private final CreateAdsDtoMapper createAdsDtoMapper;
private final FullAdsDtoMapper fullAdsDtoMapper;


    public AdsServiceImpl(AdsRepository adsRepository, AdsMapper mapper, CreateAdsDtoMapper createAdsDtoMapper, FullAdsDtoMapper fullAdsDtoMapper) {
        this.adsRepository = adsRepository;
        this.adsMapper = mapper;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.fullAdsDtoMapper = fullAdsDtoMapper;
    }

    @Override
    public AdsDto getAds(AdsDto adsDto) {
        return adsMapper.adsToAdsDto(adsRepository.findById(adsDto.getPk()).get());
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto) {
        Ads newAds = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        newAds.setDescription(createAdsDto.getDescription());
        newAds.setTitle(createAdsDto.getTitle());
        newAds.setPrice(createAdsDto.getPrice());
        adsRepository.save(newAds);
        return adsMapper.adsToAdsDto(newAds);
    }

    @Override
    public FullAdsDto getFullAds(FullAdsDto fullAdsDto) {
        return fullAdsDto;
    }

    @Override
    public FullAdsDto removeFullAds(FullAdsDto fullAdsDto) {
        adsRepository.delete(fullAdsDtoMapper.fullAdsDtoToads(fullAdsDto));
        return fullAdsDto;
    }

    @Override
    public FullAdsDto updateAds(CreateAdsDto createAdsDto) {
        return null;
    }
}
