package ru.skypro.homework.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.mapper.annotations.ImageToPathMapper;

import java.util.ArrayList;
import java.util.List;



@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "image", target = "image", qualifiedBy = ImageToPathMapper.class)
    AdsDto adsToAdsDto(Ads ads);

    @ImageToPathMapper
    default List<String> ImageToPath(List<Image> images) {
        if (images == null) {
            return null;
        } else {
            List<String> list = new ArrayList<>();
            for (Image value : images) {
                list.add(value.getFilePath());
            }
            return list;
        }
    }
}
