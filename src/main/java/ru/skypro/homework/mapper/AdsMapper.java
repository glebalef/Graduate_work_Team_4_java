package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(target = "author", source = "user.id" )
    AdsDto adsToAdsDto(Ads source);

    @Mapping(target = "user.id", source = "author")
    Ads adsDtoToAds(AdsDto source);
}
