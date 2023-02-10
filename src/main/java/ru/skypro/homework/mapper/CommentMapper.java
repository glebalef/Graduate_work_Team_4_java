package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;

@Mapper (componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToDto(Comment comment);

    Comment commentDtoToEntity(CommentDto commentDto);
}