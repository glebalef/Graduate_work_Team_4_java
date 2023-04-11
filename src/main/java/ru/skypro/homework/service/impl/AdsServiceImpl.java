
package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
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
import ru.skypro.homework.service.SecurityService;

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
    private final SecurityService securityService;


    /**
     * Метод поиска объявления по его идентификатору в БД.
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     *
     * @param pk идентификатор искомого объявления
     * @return найденное объявление с маппингом на дто
     */
    @Override
    public AdsDto getAds(Long pk) {
        logger.info("Invoke method getAds");
        Ads ads = adsRepository.findById(pk).orElse(null);
        assert ads != null;
        return adsMapper.adsToAdsDto(ads);
    }

    /**
     * Метод просто сохранения объявления в БД.
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param ads новое объявление
     */
    @Override
    public void justSaveAds(Ads ads) {
        logger.info("Invoke method justSaveAds");
        adsRepository.save(ads);
    }

    /**
     * Метод просто сохранения объявления в БД.
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * Используется метод репозитория {@link ru.skypro.homework.repository.UserRepository#findByEmail(String)}
     *
     * @param createAdsDto   новое объявление
     * @param authentication аутентификация пользователя
     * @return сохраненное объявление с маппингом на дто
     */
    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, Authentication authentication) {
        logger.info("Invoke method addAds");
        Ads ads = createAdsDtoMapper.createAdsDtoToAds(createAdsDto);
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        ads.setUserInfo(userInfo);
        adsRepository.save(ads);
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    /**
     * Метод поиска полного объявления по его идентификатору в БД.
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     *
     * @param pk идентификатор искомого объявления
     * @return найденное полное объявление с маппингом на дто
     */
    @Override
    public FullAdsDto getFullAds(Long pk) {
        logger.info("Invoke method getFullAds");
        return fullAdsMapper.adsToFullAdsDto(Objects.requireNonNull(adsRepository.findById(pk).orElse(null)));
    }

    /**
     * Метод удаления объявления по его идентификатору в БД.
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param pk             идентификатор объявления
     * @param authentication аутентификация пользователя
     */
    @Override
    public void removeFullAds(Long pk, Authentication authentication) {
        logger.info("Invoke method removeFullAds");
        if (securityService.accessAds(authentication, pk))
            adsRepository.deleteById(pk);
    }

    /**
     * Метод обновления объявления
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param pk             идентификатор объявления
     * @param createAdsDto   маппинг объявления
     * @param authentication аутентификация пользователя
     * @return обновленное объявление с маппингом на дто
     */
    @Override
    public AdsDto updateAds(Long pk, CreateAdsDto createAdsDto, Authentication authentication) {
        logger.info("Invoke method updateAds");
        Ads ads = adsRepository.findById(pk).orElse(null);
        if (securityService.accessAds(authentication, pk)) {
            assert ads != null;
            ads.setPrice(createAdsDto.getPrice());
            ads.setDescription(createAdsDto.getDescription());
            ads.setTitle(createAdsDto.getTitle());
            adsRepository.save(ads);
        }
        return adsMapper.adsToAdsDto(ads);
    }

    /**
     * Метод поиска всех объявлений у аутентифицированных пользователей
     * <br>
     * Используется метод репозитория {@link ru.skypro.homework.repository.UserRepository#findByEmail(String)}
     * Используется метод репозитория {@link ru.skypro.homework.repository.AdsRepository#findAllByUserInfoId(Long)}
     *
     * @param authentication аутентификация пользователя
     * @return найденные объявления с маппингом на дто
     */
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

    /**
     * Метод поиска всех объявлений у неаутентифицированных пользователей
     * <br>
     * Используется метод репозитория {@link JpaRepository#findAll()}
     *
     * @return найденные объявления с маппингом на дто
     */
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

    /**
     * Метод поиска объявления без дто
     * <br>
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id идентификатор искомого объявления
     * @return найденное объявление без маппинга на дто
     */
    @Override
    public Ads getAdsNotDtoById(Long id) {
        logger.info("Invoke method getAdsNotDtoById");
        return adsRepository.findById(id).orElse(null);
    }

}