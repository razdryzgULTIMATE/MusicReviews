package ru.serega.musicreviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.AlbumResponseDTO;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.services.album.AlbumService;

@RestController
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @PostMapping("/admin/album")
    public ResponseEntity<?> createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO) {
        AlbumResponseDTO album = albumService.createAlbum(albumRequestDTO);
        return ResponseEntity.ok(album);
    }

    @PutMapping("/admin/album/{albumId}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long albumId, @RequestBody AlbumRequestDTO albumRequestDTO) {
        AlbumResponseDTO album = albumService.updateAlbum(albumId, albumRequestDTO);
        return ResponseEntity.ofNullable(album);
    }

    @GetMapping("/album/all")
    public ResponseEntity<?> allAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<AlbumResponseDTO> getAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ofNullable(albumService.getAlbum(albumId));
    }
    @GetMapping("/album/by-genre")
    public ResponseEntity<?> getAlbumByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(albumService.getAlbumsByGenre(genre));
    }
    @GetMapping("/album/by-title-and-artists")
    public ResponseEntity<?> getAlbumByTitleAndArtists(@RequestBody AlbumRequestDTO albumRequestDTO) {
        return ResponseEntity.ofNullable(albumService.getAlbumByTitleAndArtists(albumRequestDTO));
    }
    @DeleteMapping("/admin/album/{albumId}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long albumId) {
        albumService.deleteAlbum(albumId);
        return ResponseEntity.noContent().build();
    }


}
