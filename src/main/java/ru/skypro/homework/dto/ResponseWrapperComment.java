package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Comment;

@Data
public class ResponseWrapperComment {
    private int count;
    private Comment[] results;

}
