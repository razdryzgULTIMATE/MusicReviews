package ru.serega.musicreviews.services.gener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.GenreDTO;
import ru.serega.musicreviews.entities.Genre;
import ru.serega.musicreviews.repos.GenreRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepo genreRepo;

    @Override
    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreRepo.findAll();
        List<GenreDTO> genresDTO = new ArrayList<>();
        for (Genre genre : genres) {
            GenreDTO genreDTO = new GenreDTO(genre.getName());
            genresDTO.add(genreDTO);
        }
        return genresDTO;
    }

    @Override
    public GenreDTO getGenre(Long id) {
        Optional<Genre> genre = genreRepo.findById(id);
        return genre.map(value -> new GenreDTO(value.getName())).orElse(null);
    }

    @Override
    public GenreDTO createGenre(GenreDTO genre) {
        Genre newGenre = new Genre();
        String correctName = getCorrectGenreName(genre.getName());
        newGenre.setName(correctName);
        Genre saved = genreRepo.save(newGenre);
        return new GenreDTO(saved.getName());
    }

    @Override
    public GenreDTO updateGenre(Long id, GenreDTO genre) {
        Optional<Genre> find = genreRepo.findById(id);
        if (find.isPresent()) {
            Genre newGenre = new Genre();
            newGenre.setName(getCorrectGenreName(genre.getName()));
            genreRepo.save(newGenre);
            return new GenreDTO(newGenre.getName());
        }
        return null;
    }

    @Override
    public GenreDTO getGenreByName(String genreName) {
        Optional<Genre> genre = genreRepo.findByNameIgnoreCase(genreName);
        return genre.map(value -> new GenreDTO(value.getName())).orElse(null);
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepo.deleteById(id);
    }

    private String getCorrectGenreName(String genre) {
        char first = genre.charAt(0);
        return Character.toString(first).toUpperCase() + genre.substring(1).toLowerCase();
    }
}
