package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serega.musicreviews.entities.Genre;

import java.util.Optional;

public interface GenreRepo extends JpaRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String genreName);
}
