package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Comment;

import java.util.ArrayList;

@Data
public class ResponseWrapperComment {
    private int count;
    private ArrayList <Comment> results;

}
