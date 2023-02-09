package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class FullAdsDto {

    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private List<String> image;
    private String phone;
    private int pk;
    private int price;
    private String title;
}
