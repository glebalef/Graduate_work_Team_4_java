package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.Comment;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @NotNull List<Comment> findCommentsByAds_Pk(Long ads_pk);

    @Query(value = "SELECT user_info_id from comments", nativeQuery = true)
    Long findUserInfoId(Long commentId);

}