package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Avatar;


public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findAvatarByUserInfoId(Long id);

}
