package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude
    @OneToMany (
            mappedBy = "ads",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List <Image> image;

    @ToString.Exclude
    @OneToMany (
            mappedBy = "ads",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments;
    @Column(name = "price")
    private Long price;

}
