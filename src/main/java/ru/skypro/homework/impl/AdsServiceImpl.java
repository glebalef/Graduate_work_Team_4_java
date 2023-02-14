package ru.skypro.homework.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.mapper.FullAdsDtoMapper;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

@Service
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
    public AdsDto getAds(Long pk) {

        return adsMapper.adsToAdsDto(adsRepository.findById(pk).get());
    }

    @Override
    public CreateAdsDto addAds(CreateAdsDto createAdsDto) {
        adsRepository.save(createAdsDtoMapper.createAdsDtoToAds(createAdsDto));
        return createAdsDto;
    }

    @Override
    public FullAdsDto getFullAds(Long pk) {
        adsRepository.findById(pk).get();
        return fullAdsDtoMapper.adsToFullAdsDto( adsRepository.findById(pk).get());
    }

    @Override
    public FullAdsDto removeFullAds(Long pk) {
        FullAdsDto result = fullAdsDtoMapper.adsToFullAdsDto(adsRepository.findById(pk).get());
        adsRepository.delete(adsRepository.findById(pk).get());
        return result;
    }

    @Override
    public FullAdsDto updateAds(Long pk, CreateAdsDto createAdsDto) {
        Ads oldAds = adsRepository.findById(pk).get();
        Ads newAds = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        oldAds.setPrice(newAds.getPrice());
        oldAds.setTitle(newAds.getTitle());
        oldAds.setDescription(newAds.getDescription());
        adsRepository.save(oldAds);
        return fullAdsDtoMapper.adsToFullAdsDto(oldAds);
    }
}
