package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

import java.util.ArrayList;
import java.util.List;

@Component
public class FullAdsMapperSamopiska {

    public FullAdsDto adsToFullAdsDtoSamopiska (Ads ads) {
        FullAdsDto dto = new FullAdsDto();
        dto.setDescription(ads.getDescription());
        dto.setTitle(ads.getTitle());
        dto.setPk(ads.getPk());
        dto.setPrice(Math.toIntExact(ads.getPrice()));
        if (ads.getUser() == null) {
            dto.setAuthorFirstName(null);
            dto.setAuthorLastName(null);
            dto.setEmail(null);
            dto.setPhone(null);
        } else {
            dto.setAuthorFirstName(ads.getUser().getFirstName());
            dto.setAuthorLastName(ads.getUser().getLastName());
            dto.setEmail(ads.getUser().getEmail());
            dto.setPhone(ads.getUser().getPhone());
        }

        List<String> list = new ArrayList<>();
        if (ads.getImage() == null) {
            dto.setImage(null);
        } else {
            for (Image img : ads.getImage()) {
                list.add(img.getFilePath());
            }
            dto.setImage(list);
        }
        return dto;
    }
}
