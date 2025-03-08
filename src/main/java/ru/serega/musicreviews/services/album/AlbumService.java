package ru.serega.musicreviews.services.album;


import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.AlbumResponseDTO;

import java.util.List;

public interface AlbumService {
    AlbumResponseDTO createAlbum(AlbumRequestDTO album);
    AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO album);
    List<AlbumResponseDTO> getAllAlbums();
    AlbumResponseDTO getAlbum(Long id);
    void deleteAlbum(Long id);
    AlbumResponseDTO getAlbumByNameAndArtist(AlbumRequestDTO albumRequestDTO);
    List<AlbumResponseDTO> getAlbumsByGenre(String genre);
}
