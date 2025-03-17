package ru.serega.musicreviews.services.track;

import ru.serega.musicreviews.dtos.TrackDTO;
import ru.serega.musicreviews.entities.Album;

import java.util.List;

public interface TrackService {
    TrackDTO createTrack(TrackDTO track);
    TrackDTO updateTrack(Long id, TrackDTO track);
    TrackDTO getTrackById(Long id);
    List<TrackDTO> getTracks();
    void deleteTrack(Long id);

}
