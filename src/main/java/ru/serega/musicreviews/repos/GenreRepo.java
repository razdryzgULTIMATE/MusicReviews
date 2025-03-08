package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serega.musicreviews.entities.Genre;

public interface GenreRepo extends JpaRepository<Genre, Long> {
}
