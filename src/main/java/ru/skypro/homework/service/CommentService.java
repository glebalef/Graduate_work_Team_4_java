package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;

public interface CommentService {
    public CommentDto addComments (CommentDto commentDto);

    public CommentDto getComments(Long id);

    public CommentDto updateComments(Long author, Long id, CommentDto commentDto);
}
