package ru.serega.musicreviews.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serega.musicreviews.dtos.TrackDTO;
import ru.serega.musicreviews.services.track.TrackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @PostMapping("/admin/track")
    public ResponseEntity<?> createTrack(@RequestBody TrackDTO trackDTO) {
        try {
            return ResponseEntity.status(201).body(trackService.createTrack(trackDTO));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Альбом не найден!");
        }

    }
    @PutMapping("/admin/track/{trackId}")
    public ResponseEntity<?> updateTrack(@PathVariable Long trackId, @RequestBody TrackDTO trackDTO) {
        try{
            return ResponseEntity.ofNullable(trackService.updateTrack(trackId, trackDTO));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(404).body("Альбом не найден!");
        }

    }
    @GetMapping("/track")
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        return ResponseEntity.ok(trackService.getTracks());
    }
    @GetMapping("/track/{trackId}")
    public ResponseEntity<TrackDTO> getTrack(@PathVariable Long trackId) {
        return ResponseEntity.ofNullable(trackService.getTrackById(trackId));
    }
    @DeleteMapping("/admin/track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable Long trackId) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.noContent().build();
    }
}
