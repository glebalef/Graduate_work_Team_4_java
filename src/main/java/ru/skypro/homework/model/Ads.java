package ru.skypro.homework.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    @ElementCollection
    private ArrayList<String> image;
    private String phone;
    private int price;
    private String type;
}
