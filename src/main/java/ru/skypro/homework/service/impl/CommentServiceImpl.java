package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdsRepository adsRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper,
                              AdsRepository adsRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;

        this.adsRepository = adsRepository;
    }

    @Override
    public CommentDto addComments(CommentDto commentDto, Long adPk) {
        logger.debug("Invoke method addComments");
        Comment newComment = new Comment();
        newComment.setCreatedAt(commentDto.getCreatedAt());
        newComment.setText(commentDto.getText());
        newComment.setAds(adsRepository.findById(adPk).orElse(null));
        commentRepository.save(newComment);
        return commentMapper.commentToDto(newComment);
    }

    @Override
    public CommentDto getComments(Long id) {
        logger.debug("Invoke method getComments");
        return commentMapper.commentToDto(commentRepository.findById(id).orElse(null));
    }

    @Override
    public CommentDto updateComments(Long author, Long id, CommentDto commentDto) {
        logger.debug("Invoke method updateComments");
        Optional <Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            Comment foundComment =optional.get();
            foundComment.setCreatedAt(commentDto.getCreatedAt());
            foundComment.setText(commentDto.getText());
            commentRepository.save(foundComment);
            return commentMapper.commentToDto(foundComment);
        }
        return null;
    }

    public void deleteComments(Long id) {
        logger.debug("Invoke method deleteComments");
        commentRepository.deleteById(id);
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
}
