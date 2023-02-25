package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.entity.Ads;

@Mapper(componentModel = "spring")
public interface CreateAdsDtoMapper {

    Ads createAdsDtoToAds(CreateAdsDto createAdsDto);

    CreateAdsDtoMapper INSTANCE = Mappers.getMapper(CreateAdsDtoMapper.class);
}
