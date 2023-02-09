package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
