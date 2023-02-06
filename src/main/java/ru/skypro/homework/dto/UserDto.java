package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String firstName;
    private Integer id;
    private String lastName;
    private String phone;
    private String regDate;
    private String city;
    private String image;

}
