package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;
    @Column(name = "description")
    private String description;
    @Column(name = "title")
    private String title;
    @ManyToOne
    private UserInfo userInfo;
    @OneToMany (
            mappedBy = "ads",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List <Image> image;

    @OneToMany (
            mappedBy = "ads",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments;
    @Column(name = "price")
    private Long price;
}
