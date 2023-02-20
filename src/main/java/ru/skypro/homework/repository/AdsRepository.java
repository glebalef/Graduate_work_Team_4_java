package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    public List<Ads> findAllByUserId (Long id);
}
