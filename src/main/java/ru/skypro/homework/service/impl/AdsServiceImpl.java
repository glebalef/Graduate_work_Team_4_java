
package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.mapper.FullAdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    private final AdsRepository adsRepository;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final UserRepository userRepository;


    @Override
    public AdsDto getAds(Long pk) {
        logger.info("Invoke method getAds");
        Ads ads = adsRepository.findById(pk).orElse(null);
        assert ads != null;
        return adsMapper.adsToAdsDto(ads);
    }

    @Override
    public void justSaveAds(Ads ads) {
        logger.info("Invoke method justSaveAds");
        adsRepository.save(ads);
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, Authentication authentication) {
        logger.info("Invoke method addAds");
        Ads ads = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        ads.setUserInfo(userInfo);
        adsRepository.save(ads);
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    @Override
    public FullAdsDto getFullAds(Long pk) {
        logger.info("Invoke method getFullAds");
        return fullAdsMapper.adsToFullAdsDto(Objects.requireNonNull(adsRepository.findById(pk).orElse(null)));
    }

    @Override
    public void removeFullAds(Long pk, Authentication authentication) {
        logger.info("Invoke method removeFullAds");
        if (accessAds(authentication, pk))
            adsRepository.deleteById(pk);
    }

    @Override
    public AdsDto updateAds(Long pk, CreateAdsDto createAdsDto, Authentication authentication) {
        logger.info("Invoke method updateAds");
        Ads ads = adsRepository.findById(pk).orElse(null);
        if (accessAds(authentication, pk)) {
            assert ads != null;
            ads.setPrice(createAdsDto.getPrice());
            ads.setDescription(createAdsDto.getDescription());
            ads.setTitle(createAdsDto.getTitle());
            adsRepository.save(ads);
        }
        return adsMapper.adsToAdsDto(ads);
    }

    @Override
    public ResponseWrapperAdsDto getAllAds(Authentication authentication) {
        logger.info("Invoke method getAllAds for authentication");
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        ResponseWrapperAdsDto wrapper = new ResponseWrapperAdsDto();
        List<AdsDto> list = new ArrayList<>();
        if (adsRepository.findAllByUserInfoId(userInfo.getId()).isEmpty()) {
            List<AdsDto> list1 = new ArrayList<>();
            wrapper.setResults(list1);
            wrapper.setCount(0);
            return wrapper;
        }
        for (Ads value : adsRepository.findAllByUserInfoId(userInfo.getId())) {
            list.add(adsMapper.adsToAdsDto(value));
            wrapper.setResults(list);
            wrapper.setCount(list.size());
        }
        return wrapper;

    }


    @Override
    public ResponseWrapperAdsDto getAll() {
        logger.info("Invoke method getAllAds");
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
        logger.info("Invoke method getAdsNotDtoById");
        return adsRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseWrapperAdsDto searchAds(String part) {
        logger.info("Invoke method searchAds");
        ResponseWrapperAdsDto wrapper = new ResponseWrapperAdsDto();
        List<AdsDto> list = new ArrayList<>();
        for (Ads value : adsRepository.findAdsByTitleOrDescriptionContainingIgnoreCase(part, part)) {
            list.add(adsMapper.adsToAdsDto(value));
        }
        wrapper.setResults(list);
        wrapper.setCount(list.size());
        return wrapper;
    }

    public boolean accessAds(Authentication authentication, Long adsId) {
        logger.info("Invoke method accessAds");
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        Long userId = ads.getUserInfo().getId();
        boolean role = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (userInfo.getId().equals(userId) || role) {
            return true;
        }
        return false;
    }

    public Ads findAdsByImageId(Long id) {
       return adsRepository.findAdsByImageId(id);
    }
}