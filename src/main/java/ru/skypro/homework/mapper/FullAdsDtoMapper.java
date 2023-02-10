package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

@Mapper(componentModel = "spring")
public interface FullAdsDtoMapper {
   FullAdsDtoMapper INSTANCE = Mappers.getMapper(FullAdsDtoMapper.class);

    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    FullAdsDto adsToFullAdsDto(Ads ads);
    @Mapping(source = "authorFirstName", target = "user.firstName")
    @Mapping(source = "authorLastName", target = "user.lastName" )
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "phone", target = "user.phone")
    Ads fullAdsDtoToads(FullAdsDto fullAdsDto);
}
