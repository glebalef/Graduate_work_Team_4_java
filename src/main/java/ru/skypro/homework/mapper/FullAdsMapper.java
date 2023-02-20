package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.mapper.annotations.ImageToPathMapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FullAdsMapper {
    FullAdsMapper INSTANCE = Mappers.getMapper(FullAdsMapper.class);

    @Mapping(source = "image", target = "image", qualifiedBy = ImageToPathMapper.class)
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    FullAdsDto adsToFullAdsDto(Ads ads);

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