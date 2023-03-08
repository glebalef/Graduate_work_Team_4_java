package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "avatar.filePath", target = "image")
    @Mapping(source = "userInfo.id", target = "id")
    UserDto userToUserDto(UserInfo userInfo, Avatar avatar);

    @Mapping(source = "username", target = "email")
    @Mapping(target = "avatar.filePath", ignore = true)
    UserInfo registerReqToUser(RegisterReq registerReq);

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


}
