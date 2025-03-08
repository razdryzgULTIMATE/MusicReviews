package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serega.musicreviews.entities.Tag;

import java.util.Optional;

public interface TagRepo extends JpaRepository<Tag, Long> {
    // Найти тег по имени (для предотвращения дубликатов)
    Optional<Tag> findByName(String name);
}
