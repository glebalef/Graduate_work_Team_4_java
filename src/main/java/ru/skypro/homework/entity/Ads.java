package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private String description;
    private String title;
    @ManyToOne
    private User user;
    @OneToMany (
            mappedBy = "ads",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List <Image> image;
    private Long price;
}
