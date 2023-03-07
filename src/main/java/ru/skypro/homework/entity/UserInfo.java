package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "users_info")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String email;
    private String firstName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String phone;
    private String regDate;
    private String city;
    private String password;

}



