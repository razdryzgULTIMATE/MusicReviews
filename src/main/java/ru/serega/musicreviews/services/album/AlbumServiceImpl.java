package ru.serega.musicreviews.services.album;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.AlbumResponseDTO;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.entities.Artist;
import ru.serega.musicreviews.entities.Genre;
import ru.serega.musicreviews.entities.Tag;
import ru.serega.musicreviews.repos.AlbumRepo;
import ru.serega.musicreviews.repos.ArtistRepo;
import ru.serega.musicreviews.repos.GenreRepo;
import ru.serega.musicreviews.repos.TagRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepo albumRepo;
    private final ArtistRepo artistRepo;
    private final GenreRepo genreRepo;
    private final TagRepo tagRepo;

    @Override
    public AlbumResponseDTO createAlbum(AlbumRequestDTO album) {
        Genre genre = genreRepo.findById(album.getGenreId()).orElseThrow(
                () -> new EntityNotFoundException("Genre not found with id: " + album.getGenreId()));
        Set<Tag> tags = tagRepo.findByNameIn(album.getTags());
        Set<Artist> artists = new HashSet<>(artistRepo.findAllById(album.getArtistIds()));

        Album albumEntity = new Album();
        albumEntity.setTitle(album.getTitle());
        albumEntity.setReleaseDate(album.getReleaseDate());
        albumEntity.setArtists(artists);
        albumEntity.setCover(album.getCover());
        albumEntity.setGenre(genre);
        albumEntity.setTags(tags);
        Album saved = albumRepo.save(albumEntity);

        return new AlbumResponseDTO(saved);
    }

    @Override
    public AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO album) {
        Optional<Album> find = albumRepo.findById(id);
        if (find.isPresent()) {
            Album entity = find.get();

            Genre genre = genreRepo.findById(album.getGenreId()).orElseThrow(
                    () -> new EntityNotFoundException("Genre not found with id: " + album.getGenreId())
            );
            Set<Tag> tags = tagRepo.findByNameIn(album.getTags());
            Set<Artist> artists = new HashSet<>(artistRepo.findAllById(album.getArtistIds()));
            entity.setGenre(genre);
            entity.setTags(tags);
            entity.setArtists(artists);
            entity.setCover(album.getCover());
            entity.setReleaseDate(album.getReleaseDate());
            entity.setTitle(album.getTitle());

            Album updated = albumRepo.save(entity);
            return new AlbumResponseDTO(updated);
        }
        return null;
    }

    @Override
    public List<AlbumResponseDTO> getAllAlbums() {
        List<Album> albums = albumRepo.findAll();
        return convertAlbumToAlbumDTO(albums);
    }

    @Override
    public AlbumResponseDTO getAlbum(Long id) {
        Optional<Album> find = albumRepo.findById(id);
        return find.map(AlbumResponseDTO::new).orElse(null);
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepo.deleteById(id);
    }

    @Override
    public AlbumResponseDTO getAlbumByTitleAndArtists(AlbumRequestDTO albumRequestDTO) {
        Set<Artist> artists = new HashSet<>(artistRepo.findAllById(albumRequestDTO.getArtistIds()));
        Optional<Album> find = albumRepo.findByTitleAndArtists(albumRequestDTO.getTitle(), artists);
        return find.map(AlbumResponseDTO::new).orElse(null);
    }

    @Override
    public List<AlbumResponseDTO> getAlbumsByGenre(String genre) {
        Genre findGenre = genreRepo.findByNameIgnoreCase(genre).orElseThrow();
        List<Album> albums = albumRepo.findByGenre(findGenre);
        return convertAlbumToAlbumDTO(albums);
    }
    private List<AlbumResponseDTO> convertAlbumToAlbumDTO(List<Album> albums) {
        return albums.stream().map(AlbumResponseDTO::new).collect(Collectors.toList());
    }
}
