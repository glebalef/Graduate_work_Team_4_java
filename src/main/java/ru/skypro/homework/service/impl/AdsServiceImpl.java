
        package ru.skypro.homework.service.impl;

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
        import ru.skypro.homework.repository.AvatarRepository;
        import ru.skypro.homework.service.AdsService;
        import ru.skypro.homework.service.ImageService;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Objects;

        @Service
        public class AdsServiceImpl implements AdsService {

            private final AdsRepository adsRepository;
            private final CreateAdsDtoMapper createAdsDtoMapper;
            private final AdsMapper adsMapper;
            private final FullAdsMapper fullAdsMapper;
            private final AvatarRepository avatarRepository;


            public AdsServiceImpl(AdsRepository adsRepository,
                                  CreateAdsDtoMapper createAdsDtoMapper, AdsMapper adsMapper, FullAdsMapper fullAdsMapper, ImageService imageService,
                                  AvatarRepository avatarRepository) {
                this.adsRepository = adsRepository;
                this.createAdsDtoMapper = createAdsDtoMapper;
                this.adsMapper = adsMapper;
                this.fullAdsMapper = fullAdsMapper;
                this.avatarRepository = avatarRepository;
            }

            @Override
            public AdsDto getAds(Long pk) {
                Ads ads = adsRepository.findById(pk).orElse(null);
                assert ads != null;
                return adsMapper.adsToAdsDto(ads);
            }

            @Override
            public void justSaveAds (Ads ads) {
                adsRepository.save(ads);
            }

            @Override
            public AdsDto addAds(CreateAdsDto createAdsDto) {
                Ads ads = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
                adsRepository.save(ads);
                return adsMapper.adsToAdsDto(adsRepository.save(ads));
            }

            @Override
            public FullAdsDto getFullAds(Long pk) {
                FullAdsDto Dto = fullAdsMapper.adsToFullAdsDto(Objects.requireNonNull(adsRepository.findById(pk).orElse(null)));


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

            @Override
            public ResponseWrapperAdsDto getAllAds(Long id) {
                return null;
            }


            @Override
            public ResponseWrapperAdsDto getAll() {
                ResponseWrapperAdsDto wrapper = new ResponseWrapperAdsDto();
                List<AdsDto> list = new ArrayList<>();
                for (Ads value : adsRepository.findAll()) {
                    list.add(adsMapper.adsToAdsDto(value));
                }
                wrapper.setResults(list);
                wrapper.setCount(list.size());
                return wrapper;

            }

            @Override
            public Ads getAdsNotDtoById(Long id) {
                return adsRepository.findById(id).orElse(null);
            }

            @Override
            public ResponseWrapperAdsDto searchAds(String part) {
                ResponseWrapperAdsDto wrapper = new ResponseWrapperAdsDto();
                List<AdsDto> list = new ArrayList<>();
                for (Ads value : adsRepository.findAdsByTitleOrDescriptionContainingIgnoreCase(part, part)) {
                    list.add(adsMapper.adsToAdsDto(value));
                }
                wrapper.setResults(list);
                wrapper.setCount(list.size());
                return wrapper;
            }
        }