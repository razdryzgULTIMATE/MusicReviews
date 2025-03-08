package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serega.musicreviews.entities.Artist;

public interface ArtistRepo extends JpaRepository<Artist, Long> {
}
