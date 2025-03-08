package ru.serega.musicreviews.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.serega.musicreviews.entities.Album;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponseDTO {
    private Long id;
    private String title;
    private Set<ArtistDTO> artists;
    private GenreDTO genre;
    private LocalDate releaseDate;
    private String cover;
    private Set<TagDTO> tags;
    private List<ReviewResponseDTO> reviews;
    public AlbumResponseDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.artists = album.getArtists().stream().map(ArtistDTO::new).collect(Collectors.toSet());
        this.genre = new GenreDTO();
        this.cover = album.getCover();
        this.genre.setName(album.getGenre().getName());
        this.releaseDate = album.getReleaseDate();
        this.tags = album.getTags().stream().map(t -> new TagDTO(t.getName())).collect(Collectors.toSet());
        this.reviews = album.getReviews().stream().map(ReviewResponseDTO::new).collect(Collectors.toList());
    }
}
