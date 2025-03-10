package ru.serega.musicreviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.ArtistDTO;
import ru.serega.musicreviews.services.artist.ArtistService;

@RestController
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @PostMapping("/admin/artist")
    public ResponseEntity<?> createArtist(@RequestBody ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.createArtist(artistDTO));
    }

    @PutMapping("admin/artist/{artistId}")
    public ResponseEntity<?> updateArtist(@RequestBody ArtistDTO artistDTO, @PathVariable Long artistId) {
        return ResponseEntity.ofNullable(artistService.updateArtist(artistId, artistDTO));
    }

    @GetMapping("/artist/all")
    public ResponseEntity<?> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<?> getArtist(@PathVariable Long artistId) {
        return ResponseEntity.ofNullable(artistService.getArtist(artistId));
    }

    @GetMapping("/artist/by-album")
    public ResponseEntity<?> getArtistByAlbum(@RequestBody AlbumRequestDTO albumDTO) {
        return ResponseEntity.ofNullable(artistService.getAllArtistsByAlbum(albumDTO));
    }

    @DeleteMapping("/admin/artist/{artistId}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long artistId) {
        artistService.deleteArtist(artistId);
        return ResponseEntity.noContent().build();
    }
}
