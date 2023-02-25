package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;


    @Override
    public CommentDto addComments(CommentDto commentDto, Long adPk, Authentication authentication) {
        logger.debug("Invoke method addComments");
        Comment newComment = new Comment();
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        newComment.setCreatedAt(LocalDateTime.now().toString());
        newComment.setText(commentDto.getText());
        newComment.setAds(adsRepository.findById(adPk).orElse(null));
        newComment.setUserInfo(userInfo);
        newComment = commentRepository.save(newComment);
        return commentMapper.commentToDto(newComment);
    }

    @Override
    public CommentDto getComments(Long id) {
        logger.debug("Invoke method getComments");
        return commentMapper.commentToDto(commentRepository.findById(id).orElse(null));
    }

    @Override
    public CommentDto updateComments(Long author, Long id, CommentDto commentDto, Authentication authentication) {
        logger.debug("Invoke method updateComments");
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent() && accessComments(authentication, author, id)) {
            Comment foundComment = optional.get();
            foundComment.setCreatedAt(LocalDate.now().toString());
            foundComment.setText(commentDto.getText());
            commentRepository.save(foundComment);
            return commentMapper.commentToDto(foundComment);
        }
        return null;
    }

    public void deleteComments(Authentication authentication, Long adsId, Long commentId) {
        logger.debug("Invoke method deleteComments");
        if (accessComments(authentication, adsId, commentId)) {
            commentRepository.deleteById(commentId);
        }
    }

    @Override
    public ResponseWrapperComment getAll(Long adPk) {
        List<CommentDto> list = new ArrayList<>();
        ResponseWrapperComment wrapper = new ResponseWrapperComment();
        for (Comment value : commentRepository.findCommentsByAds_Pk(adPk)) {
            list.add(commentMapper.commentToDto(value));
        }
        wrapper.setResults(list);
        wrapper.setCount(list.size());
        return wrapper;

    }

    public boolean accessComments(Authentication authentication, Long adsId, Long commentId) {
        UserInfo userInfo = userRepository.findByEmail(authentication.getName());
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Long userIdComment = comment.getUserInfo().getId();
        Long userIdAds = ads.getUserInfo().getId();
        boolean role = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (userInfo.getId().equals(userIdComment)
                || userInfo.getId().equals(userIdAds) || role) {
            return true;
        }
        return false;
    }
}
