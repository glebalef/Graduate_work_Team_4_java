package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Image;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByAdsPk(Long adsPk);

    List<Image> findImagesByAds_Pk(Long ads_pk);

    Image findImageByAds_pk(Long ads_pk);

    Void deleteImageByAds_pk(Long pk);
}
