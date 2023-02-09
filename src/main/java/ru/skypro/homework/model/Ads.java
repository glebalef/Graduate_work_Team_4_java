package ru.skypro.homework.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private String description;
    @ManyToOne
    private User user;
    @Transient
    private List <String> image;
    private Long price;
    private String title;
}
