package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.serega.musicreviews.entities.Track;

@Repository
public interface TrackRepo extends JpaRepository<Track, Long> {

}
