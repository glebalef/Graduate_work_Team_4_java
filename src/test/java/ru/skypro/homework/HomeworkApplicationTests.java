package ru.skypro.homework;

import nonapi.io.github.classgraph.utils.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsMapper;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HomeworkApplicationTests {

    User TEST_USER = new User(
            "abc@abc.com",
            "Ivan",
            1L,
            "Ivanov",
            "495-11-22",
            "01-01-2000",
            "Moscow",
            "Password");

    Ads TEST_ADS = new Ads(
            1L,
            "ПРОДАМ ГАРАЖ",
            "ГАРАЖ",
            TEST_USER,
            null,
            100L );

    Image TEST_IMAGE = new Image(
            1L,
            "folder/onemorefolder",
            100L,
            null,
            null,
            TEST_ADS);

    List<Image> TEST_LIST = new ArrayList<>();


    @Test
    void contextLoads() {
    }


    @Test
    void shouldReturnAdsDTO() {
        TEST_LIST.add(TEST_IMAGE);
        TEST_ADS.setImage(TEST_LIST);

        List<String> stringList = new ArrayList<>();
        stringList.add("folder/onemorefolder");

        Assertions.assertEquals(AdsMapper.INSTANCE.adsToAdsDto(TEST_ADS).getImage(), stringList);


    }

}
