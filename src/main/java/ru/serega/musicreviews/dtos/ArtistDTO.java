package ru.serega.musicreviews.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.serega.musicreviews.entities.Artist;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {
    private Long id;
    private String name;
    public ArtistDTO(Artist artist) {
        this(
                artist.getId(),
                artist.getName()
        );
    }

}
