package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
