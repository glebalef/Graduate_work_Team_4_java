package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdsDto {
    private Long author;
    private List<String> image;
    private Long pk;
    private int price;
    private String title;
}
