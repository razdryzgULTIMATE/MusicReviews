package ru.serega.musicreviews.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.serega.musicreviews.entities.Track;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDTO {
    private Long id;
    private String title;
    private Long albumId;
    public TrackDTO(Track track) {
        this.id = track.getId();
        this.title = track.getTitle();
        this.albumId = track.getAlbum().getId();
    }
}
