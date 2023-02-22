package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="users_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "reg_date")
    private String regDate;
    @Column(name = "city")
    private String city;
    @Column(name = "password")
    private String password;
}



