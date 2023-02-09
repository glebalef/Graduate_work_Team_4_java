package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.Ads;

@Mapper
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);
    AdsDto toDtoAds(Ads ads);
}
