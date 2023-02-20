
        package ru.skypro.homework.impl;

        import org.springframework.stereotype.Service;
        import ru.skypro.homework.dto.AdsDto;
        import ru.skypro.homework.dto.CreateAdsDto;
        import ru.skypro.homework.dto.FullAdsDto;
        import ru.skypro.homework.dto.ResponseWrapperAdsDto;
        import ru.skypro.homework.entity.Ads;
        import ru.skypro.homework.mapper.AdsMapper;
        import ru.skypro.homework.mapper.CreateAdsDtoMapper;
        import ru.skypro.homework.mapper.FullAdsMapper;
        import ru.skypro.homework.repository.AdsRepository;
        import ru.skypro.homework.service.AdsService;

        import java.util.Objects;

        @Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;


    public AdsServiceImpl(AdsRepository adsRepository,
                          CreateAdsDtoMapper createAdsDtoMapper, AdsMapper adsMapper, FullAdsMapper fullAdsMapper) {
        this.adsRepository = adsRepository;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.adsMapper = adsMapper;
        this.fullAdsMapper = fullAdsMapper;
    }

    @Override
    public AdsDto getAds(Long pk) {
       Ads ads =  adsRepository.findById(pk).orElse(null);
        assert ads != null;
        return adsMapper.adsToAdsDto(ads);
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto) {
        Ads ads = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        adsRepository.save(ads);
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    @Override
    public FullAdsDto getFullAds(Long pk) {
        return fullAdsMapper.adsToFullAdsDto(Objects.requireNonNull(adsRepository.findById(pk).orElse(null)));
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

        return adsMapper.adsToAdsDto(ads);
    }

    public ResponseWrapperAdsDto getAllAds (Long id) {
        ResponseWrapperAdsDto  wrapper = new ResponseWrapperAdsDto();
        wrapper.setResults(adsRepository.findAllByUserId(id));
        wrapper.setCount(adsRepository.findAllByUserId(id).size());
        return wrapper;
    }
}