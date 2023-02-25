package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto usertoUserDto(UserInfo userInfo);
    @Mapping(source = "username", target = "email")
    UserInfo registerReqToUser(RegisterReq registerReq);


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
