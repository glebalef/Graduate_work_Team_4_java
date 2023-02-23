package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail (String email);
}
