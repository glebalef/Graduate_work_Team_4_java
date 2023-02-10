package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.Ads;

@Mapper (componentModel = "spring")
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "user.id", target = "author")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(source = "author", target = "user.id")
    Ads adsDtoToAds(AdsDto adsDto);


}
