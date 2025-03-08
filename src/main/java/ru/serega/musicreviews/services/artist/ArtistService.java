package ru.serega.musicreviews.services.artist;

import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.ArtistDTO;
import ru.serega.musicreviews.entities.Album;

import java.util.List;

public interface ArtistService {
    ArtistDTO createArtist(ArtistDTO artist);
    ArtistDTO updateArtist(Long id, ArtistDTO artist);
    List<ArtistDTO> getAllArtists();
    List<ArtistDTO> getAllArtistsByAlbum(AlbumRequestDTO album);
    ArtistDTO getArtist(Long id);
    void deleteArtist(Long id);
}
