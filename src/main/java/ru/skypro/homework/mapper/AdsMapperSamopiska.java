package ru.skypro.homework.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdsMapperSamopiska {

    public AdsDto adsToAdsDtoSamopiska(Ads ads) {
        AdsDto dto = new AdsDto();
        if (ads.getUser() == null) {
            dto.setAuthor(null);
        } else {
            dto.setAuthor(ads.getUser().getId());
        }
        dto.setPk(ads.getPk());
        dto.setTitle(ads.getTitle());
        dto.setPrice(Math.toIntExact(ads.getPrice()));

        if (ads.getImage() == null) {
            dto.setImage(null);
        } else {
            List<String> list = new ArrayList<>();
            for (Image img : ads.getImage()) {
                list.add(img.getFilePath());
            }
            dto.setImage(list);

        }
        return dto;
    }
}
