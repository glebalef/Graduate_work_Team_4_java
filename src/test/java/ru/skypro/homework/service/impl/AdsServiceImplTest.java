package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
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
import ru.skypro.homework.service.SecurityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdsServiceImplTest {
    @Mock
    AdsRepository adsRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CreateAdsDtoMapper createAdsDtoMapper;
    @Mock
    AdsMapper adsMapper;
    @Mock
    private SecurityService securityService;
    @Mock
    private Authentication authentication;
    @Mock
    FullAdsMapper fullAdsMapper;
    @InjectMocks
    AdsServiceImpl adsService;

    private Ads ads;
    private UserInfo userInfo;
    private AdsDto adsDto;
    private CreateAdsDto createAdsDto;


    @BeforeEach
    void setUp() {
        userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setEmail("test@mail.com");
        ads = new Ads();
        ads.setPk(1L);
        ads.setTitle("TestTitle");
        ads.setDescription("TestDescription");
        ads.setPrice(1234L);
        ads.setUserInfo(userInfo);

        adsDto = new AdsDto();
        adsDto.setPk(ads.getPk());
        adsDto.setPrice(ads.getPrice());
        adsDto.setTitle(ads.getTitle());
        adsDto.setAuthor(userInfo.getId());
        createAdsDto = new CreateAdsDto();
        createAdsDto.setTitle(ads.getTitle());
        createAdsDto.setDescription(ads.getDescription());
        createAdsDto.setPrice(ads.getPrice());


    }

    @Test
    void getAds() {
        when(adsMapper.adsToAdsDto(ads)).thenReturn(adsDto);
        when(adsRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(ads));
        assertEquals(adsService.getAds(ads.getPk()), adsDto);
    }

    @Test
    void justSaveAds() {
        adsService.justSaveAds(ads);
        verify(adsRepository).save(ads);
    }

    @Test
    void addAds() {
        when(adsRepository.save(any(Ads.class))).thenReturn(ads);
        when(createAdsDtoMapper.createAdsDtoToAds(any(CreateAdsDto.class))).thenReturn(ads);
        when(adsMapper.adsToAdsDto(any(Ads.class))).thenReturn(adsDto);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(userInfo);
        assertEquals(adsService.addAds(createAdsDto, authentication), adsDto);
    }

    @Test
    void getFullAds() {
        when(fullAdsMapper.adsToFullAdsDto(any(Ads.class))).thenReturn(new FullAdsDto());
        when(adsRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(ads));
        assertEquals(adsService.getFullAds(userInfo.getId()), new FullAdsDto());

    }

    @Test
    void removeFullAds() {
        when(securityService.accessAds(any(Authentication.class), eq(ads.getPk()))).thenReturn(true);
        adsService.removeFullAds(ads.getPk(), authentication);
        verify(adsRepository).deleteById(ads.getPk());
    }

    @Test
    void updateAds() {

        ads.setTitle("newTitle");
        adsDto.setTitle(ads.getTitle());
        when(adsRepository.save(any(Ads.class))).thenReturn(ads);
        when(adsMapper.adsToAdsDto(any(Ads.class))).thenReturn(adsDto);
        when(securityService.accessAds(any(Authentication.class), eq(ads.getPk()))).thenReturn(true);
        when(adsRepository.findById(ads.getPk())).thenReturn(Optional.ofNullable(ads));
        assertEquals(adsService.updateAds(ads.getPk(), createAdsDto, authentication).getTitle(), adsDto.getTitle());
    }

    @Test
    void getAllAds() {
        List<Ads> adsList = new ArrayList<>();
        adsList.add(ads);
        List<AdsDto> dtoList = new ArrayList<>();
        dtoList.add(adsDto);
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        responseWrapperAdsDto.setResults(dtoList);
        responseWrapperAdsDto.setCount(dtoList.size());
        when(userRepository.findByEmail(authentication.getName())).thenReturn(userInfo);
        when(adsRepository.findAllByUserInfoId(userInfo.getId())).thenReturn(adsList);
        when(adsMapper.adsToAdsDto(ads)).thenReturn(adsDto);
        assertEquals(adsService.getAllAds(authentication), responseWrapperAdsDto);
    }

    @Test
    void getAll() {
        List<Ads> adsList = new ArrayList<>();
        adsList.add(ads);
        List<AdsDto> dtoList = new ArrayList<>();
        dtoList.add(adsDto);
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        responseWrapperAdsDto.setResults(dtoList);
        responseWrapperAdsDto.setCount(dtoList.size());
        when(adsRepository.findAll()).thenReturn(adsList);
        when(adsMapper.adsToAdsDto(ads)).thenReturn(adsDto);
        assertEquals(adsService.getAll(), responseWrapperAdsDto);

    }

    @Test
    void getAdsNotDtoById() {
        when(adsRepository.findById(ads.getPk())).thenReturn(Optional.ofNullable(ads));
        assertEquals(adsService.getAdsNotDtoById(ads.getPk()), ads);
    }
}