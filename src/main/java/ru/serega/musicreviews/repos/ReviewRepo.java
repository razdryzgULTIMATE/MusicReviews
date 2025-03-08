package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serega.musicreviews.entities.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
