package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long author;
    private String createdAt;
    private Long pk;
    private String text;
}
