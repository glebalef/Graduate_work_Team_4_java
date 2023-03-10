package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperComment;

public interface CommentService {
    CommentDto addComments(CommentDto commentDto, Long adPk, Authentication authentication);

    CommentDto getComments(Long id);

    CommentDto updateComments(Long author, Long id, CommentDto commentDto, Authentication authentication);

    void deleteComments(Authentication authentication, Long adsId, Long commentId);

    ResponseWrapperComment getAll(Long adPk);
}
