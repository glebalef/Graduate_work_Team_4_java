package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AdsDto {
    private int author;
    private ArrayList<String> image;
    private int pk;
    private int price;
    private String title;
}
