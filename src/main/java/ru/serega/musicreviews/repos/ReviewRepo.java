package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.serega.musicreviews.entities.Genre;
import ru.serega.musicreviews.entities.Review;
import ru.serega.musicreviews.entities.User;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query("SELECT a FROM Review r JOIN Album a on r.album.id = a.id WHERE a.id = :albumId")
    List<Review> findReviewsByAlbumId(@Param("albumId") Long albumId);

    List<Review> findReviewsByUser(User user);

}
