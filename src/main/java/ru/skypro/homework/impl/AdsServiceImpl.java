
        package ru.skypro.homework.impl;

        import org.springframework.stereotype.Service;
        import ru.skypro.homework.dto.AdsDto;
        import ru.skypro.homework.dto.CreateAdsDto;
        import ru.skypro.homework.dto.FullAdsDto;
        import ru.skypro.homework.entity.Ads;
        import ru.skypro.homework.entity.Image;
        import ru.skypro.homework.mapper.AdsMapperSamopiska;
        import ru.skypro.homework.mapper.CreateAdsDtoMapper;
        import ru.skypro.homework.mapper.FullAdsMapperSamopiska;
        import ru.skypro.homework.repository.AdsRepository;
        import ru.skypro.homework.repository.ImageRepository;
        import ru.skypro.homework.service.AdsService;

        import java.util.Objects;

        @Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;

    private final FullAdsMapperSamopiska samopiskaFullAds;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final AdsMapperSamopiska samopiskaAds;

    public AdsServiceImpl(AdsRepository adsRepository,  FullAdsMapperSamopiska samopiskaFullAds,
                          CreateAdsDtoMapper createAdsDtoMapper, AdsMapperSamopiska samopiskaAds) {
        this.adsRepository = adsRepository;
        this.samopiskaFullAds = samopiskaFullAds;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.samopiskaAds = samopiskaAds;
    }

    @Override
    public AdsDto getAds(Long pk) {
       Ads ads =  adsRepository.findById(pk).orElse(null);
        assert ads != null;
        return samopiskaAds.adsToAdsDtoSamopiska(ads);
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto) {
        Ads ads = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        adsRepository.save(ads);
        return samopiskaAds.adsToAdsDtoSamopiska(adsRepository.save(ads));
    }

    @Override
    public FullAdsDto getFullAds(Long pk) {
        return samopiskaFullAds.adsToFullAdsDtoSamopiska(Objects.requireNonNull(adsRepository.findById(pk).orElse(null)));
    }

    @Override
    public FullAdsDto removeFullAds(Long pk) {
        adsRepository.deleteById(pk);
        return null;
    }

    @Override
    public AdsDto updateAds(Long pk, CreateAdsDto createAdsDto) {
        Ads ads = adsRepository.findById(pk).orElse(null);
        ads.setPrice(createAdsDto.getPrice());
        ads.setDescription(createAdsDto.getDescription());
        ads.setTitle(createAdsDto.getTitle());
        adsRepository.save(ads);

        return samopiskaAds.adsToAdsDtoSamopiska(ads);
    }

}