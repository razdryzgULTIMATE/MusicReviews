package ru.serega.musicreviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.entities.Tag;

import java.util.List;
import java.util.Set;

public interface AlbumRepo extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a JOIN a.tags t WHERE t.name = :tagName")
    List<Album> findAlbumsByTagName(@Param("tagName") String tagName);


}
