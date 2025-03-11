package ru.serega.musicreviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serega.musicreviews.dtos.GenreDTO;
import ru.serega.musicreviews.services.gener.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/admin/genre")
    public ResponseEntity<?> createGenre(@RequestBody GenreDTO genre) {
        return ResponseEntity.ofNullable(genreService.createGenre(genre));
    }

    @PutMapping("/admin/genre/{genreId}")
    public ResponseEntity<?> updateGenre(@PathVariable Long genreId, @RequestBody GenreDTO genre) {
        return ResponseEntity.ofNullable(genreService.updateGenre(genreId, genre));
    }

    @GetMapping("/genre/all")
    public ResponseEntity<?> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<?> getGenre(@PathVariable Long genreId) {
        return ResponseEntity.ofNullable(genreService.getGenre(genreId));
    }

    @DeleteMapping("admin/genre/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long genreId) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.noContent().build();
    }

}
