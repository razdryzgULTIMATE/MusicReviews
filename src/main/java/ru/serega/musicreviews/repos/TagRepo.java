package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.entities.Artist;
import ru.serega.musicreviews.entities.Tag;

import java.util.Optional;
import java.util.Set;

public interface TagRepo extends JpaRepository<Tag, Long> {
    // Найти тег по имени (для предотвращения дубликатов)
    Optional<Tag> findByName(String name);
    Set<Tag> findByNameIn(Set<String> names);
    boolean existsByName(String name);
}
