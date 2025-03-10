package ru.serega.musicreviews.services.tag;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.TagDTO;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.entities.Tag;
import ru.serega.musicreviews.repos.AlbumRepo;
import ru.serega.musicreviews.repos.TagRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;
    private final AlbumRepo albumRepo;
    @Override
    public TagDTO createTag(TagDTO tag) {
        if(tagRepo.existsByName(tag.getName())) {
            throw new EntityExistsException("Tag with name '" + tag.getName() + "' already exists");
        }
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        tagRepo.save(newTag);
        return new TagDTO(newTag.getName());
    }

    @Override
    public TagDTO updateTag(Long id, TagDTO tag) {
        Optional<Tag> tagOptional = tagRepo.findById(id);
        return tagOptional.map(t -> new TagDTO(t.getName())).orElse(null);
    }

    @Override
    public TagDTO getTag(Long id) {
        Optional<Tag> tagOptional = tagRepo.findById(id);
        return tagOptional.map(t -> new TagDTO(t.getName())).orElse(null);
    }

    @Override
    public Set<TagDTO> getAllTags() {
        Set<Tag> tags = new HashSet<>(tagRepo.findAll());
        return convertToTagDTOSet(tags);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepo.deleteById(id);
    }

    private Set<TagDTO> convertToTagDTOSet(Set<Tag> tags) {
        return tags.stream().map(t -> new TagDTO(t.getName())).collect(Collectors.toSet());
    }
}
