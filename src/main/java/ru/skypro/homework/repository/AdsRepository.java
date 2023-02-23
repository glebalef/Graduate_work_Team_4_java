package ru.skypro.homework.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    public List<Ads> findAllByUserInfoId (Long id);
    @NotNull List<Ads> findAll();

    List<Ads> findAdsByTitleOrDescriptionContainingIgnoreCase(String part1, String part2);

}
