package ru.serega.musicreviews.services.track;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.TrackDTO;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.entities.Track;
import ru.serega.musicreviews.repos.AlbumRepo;
import ru.serega.musicreviews.repos.TrackRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TrackRepo trackRepo;
    private final AlbumRepo albumRepo;
    @Override
    public TrackDTO createTrack(TrackDTO dto) {
        Track track = new Track();
        Album album = albumRepo.findById(dto.getAlbumId()).orElseThrow(EntityNotFoundException::new);
        track.setTitle(dto.getTitle());
        track.setAlbum(album);
        var saved = trackRepo.save(track);
        return new TrackDTO(saved);
    }

    @Override
    public TrackDTO updateTrack(Long id, TrackDTO dto) {
        Optional<Track> trackOptional = trackRepo.findById(id);
        Album album = albumRepo.findById(dto.getAlbumId()).orElseThrow(EntityNotFoundException::new);
        if (trackOptional.isPresent()) {
            Track track = trackOptional.get();
            track.setTitle(dto.getTitle());
            track.setAlbum(album);
            var saved = trackRepo.save(track);
            return new TrackDTO(saved);
        }
        return null;
    }

    @Override
    public TrackDTO getTrackById(Long id) {
        Optional<Track> trackOptional = trackRepo.findById(id);
        return trackOptional.map(TrackDTO::new).orElse(null);
    }

    @Override
    public List<TrackDTO> getTracks() {
        List<Track> tracks = trackRepo.findAll();
        return convertToTrackDTOList(tracks);
    }

    @Override
    public void deleteTrack(Long id) {
        trackRepo.deleteById(id);
    }

    private List<TrackDTO> convertToTrackDTOList(List<Track> tracks) {
        return tracks.stream().map(TrackDTO::new).collect(Collectors.toList());
    }
}
