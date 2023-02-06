package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FullAdsDto {

    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private ArrayList<String> image;
    private String phone;
    private int pk;
    private int price;
    private String type;
}
