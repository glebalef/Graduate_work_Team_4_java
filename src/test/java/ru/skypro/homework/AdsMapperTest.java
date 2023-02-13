package ru.skypro.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Ads;

public class AdsMapperTest {

    Ads ads = new Ads();
    String test = "test";



@Test
public void shouldReturnDTOTest() {
    Assertions.assertSame(AdsMapper.INSTANCE.adsToAdsDto(ads).getClass(), AdsDto.class);
    }

    @Test
    public void shouldReturnStringTest() {
        Assertions.assertSame(test.getClass(), String.class);
    }
}
