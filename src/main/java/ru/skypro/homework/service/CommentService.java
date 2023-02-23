package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperComment;

public interface CommentService {
    CommentDto addComments (CommentDto commentDto);

    CommentDto getComments(Long id);

    CommentDto updateComments(Long author, Long id, CommentDto commentDto);

    void deleteComments(Long id);

    public ResponseWrapperComment getAll();

}
