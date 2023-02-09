package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto usertoUserDto(User user);
    User userDtotoUser(UserDto userDto);
    @Mapping(target = "newPasswordDto.password", source = "password")
    NewPasswordDto usertoNewPasswordDto(User user);
    @Mapping(target = "password", source = "newPasswordDto.password")
    User newPasswordDtotoUser(NewPasswordDto newPasswordDto);

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
