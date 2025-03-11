package ru.serega.musicreviews.services.artist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.ArtistDTO;
import ru.serega.musicreviews.entities.Artist;
import ru.serega.musicreviews.repos.AlbumRepo;
import ru.serega.musicreviews.repos.ArtistRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepo artistRepo;

    @Override
    public ArtistDTO createArtist(ArtistDTO artist) {
        Artist newArtist = new Artist();
        newArtist.setName(artist.getName());
        Artist saved = artistRepo.save(newArtist);
        return new ArtistDTO(saved);
    }

    @Override
    public ArtistDTO updateArtist(Long id, ArtistDTO artist) {
        Optional<Artist> saved = artistRepo.findById(id);
        if (saved.isPresent()) {
            Artist art = saved.get();
            art.setName(artist.getName());
            Artist updated = artistRepo.save(art);
            return new ArtistDTO(updated);
        }
        return null;
    }

    @Override
    public List<ArtistDTO> getAllArtists() {
        List<Artist> artists = artistRepo.findAll();
        return new ArrayList<>(convertToArtistDTOList(artists));
    }

    @Override
    public Set<ArtistDTO> getAllArtistsByAlbum(AlbumRequestDTO album) {
        Set<Artist> artists = new HashSet<>(artistRepo.findAllById(album.getArtistIds()));
        return new HashSet<>(convertToArtistDTOList(artists));
    }

    @Override
    public ArtistDTO getArtist(Long id) {
        Optional<Artist> saved = artistRepo.findById(id);
        return saved.map(ArtistDTO::new).orElse(null);
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepo.deleteById(id);
    }

    private Collection<ArtistDTO> convertToArtistDTOList(Collection<Artist> artists) {
        return artists.stream().map(ArtistDTO::new).collect(Collectors.toList());
    }
}
