package ru.serega.musicreviews.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
//+ Создание альбомов
public class AlbumRequestDTO {
    private Long albumId;
    private String title;
    private Set<Long> artistIds;
    private LocalDate releaseDate;
    private Long genreId;
    private String cover;
    private Set<String> tags;


}
