
package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
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
    private final AdsRepository adsRepository;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final UserRepository userRepository;


    @Override
    public AdsDto getAds(Long pk) {
        Ads ads = adsRepository.findById(pk).orElse(null);
        assert ads != null;
        return adsMapper.adsToAdsDto(ads);
    }

    @Override
    public void justSaveAds(Ads ads) {
        adsRepository.save(ads);
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, Authentication authentication) {
        Ads ads = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        ads.setUserInfo(userInfo);
        adsRepository.save(ads);
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    @Override
    public FullAdsDto getFullAds(Long pk) {

        return fullAdsMapper.adsToFullAdsDto(Objects.requireNonNull(adsRepository.findById(pk).orElse(null)));
    }

    @Override
    public void removeFullAds(Long pk, Authentication authentication) {
        if (accessAds(authentication, pk))
            adsRepository.deleteById(pk);
    }

    @Override
    public AdsDto updateAds(Long pk, CreateAdsDto createAdsDto, Authentication authentication) {

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
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        ResponseWrapperAdsDto wrapper = new ResponseWrapperAdsDto();
        List<AdsDto> list = new ArrayList<>();
        for (Ads value : adsRepository.findAllByUserInfoId(userInfo.getId())) {
            list.add(adsMapper.adsToAdsDto(value));
            wrapper.setResults(list);
            wrapper.setCount(list.size());
        }
        return wrapper;

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

    public boolean accessAds(Authentication authentication, Long adsId) {
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        boolean role = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (userInfo.getId().equals(adsRepository.findUserInfoId(adsId)) || role) {
            return true;
        }
        return false;
    }


}