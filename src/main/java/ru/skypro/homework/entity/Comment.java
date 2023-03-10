package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table (name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @ManyToOne
    private UserInfo userInfo;
    @Column(name = "created_at")
    private String createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;
    @Column(name = "text")
    private String text;

    @ManyToOne
    private Ads ads;
}