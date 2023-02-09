package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdsDto {
    private int author;
    private List<String> image;
    private int pk;
    private int price;
    private String title;
}
