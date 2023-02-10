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
    private User user;
    private String createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private String text;
}