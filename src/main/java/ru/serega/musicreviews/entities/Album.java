package ru.serega.musicreviews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String cover; //URL к обложке альбома

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany
    @JoinTable(
            name = "album_tags", // Название таблицы-связки
            joinColumns = @JoinColumn(name = "album_id"), // Столбец для текущей сущности (Album)
            inverseJoinColumns = @JoinColumn(name = "tag_id") // Столбец для связанной сущности (Tag)
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "album_artists",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>();

    //+ Добавляем методы управления связями для артиста
    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getAlbums().add(this);
    }

    public void removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getAlbums().remove(this);
    }

    //+ Методы для управления связями для тегов
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getAlbums().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getAlbums().remove(this);
    }


}
