package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.entity.Comment;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private int count;
    private List<CommentDto> results;

}
