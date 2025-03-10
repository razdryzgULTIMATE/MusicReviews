package ru.serega.musicreviews.services.gener;

import ru.serega.musicreviews.dtos.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> getAllGenres();
    GenreDTO getGenre(Long id);
    GenreDTO createGenre(GenreDTO genre);
    GenreDTO updateGenre(Long id, GenreDTO genre);
    void deleteGenre(Long id);

}
