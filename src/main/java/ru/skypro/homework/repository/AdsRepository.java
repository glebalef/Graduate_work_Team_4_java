package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

public interface AdsRepository extends JpaRepository<Ads, Long> {

}
