package ru.serega.musicreviews.services.tag;

import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.TagDTO;

import java.util.List;
import java.util.Set;

public interface TagService {
    TagDTO createTag(TagDTO tag);
    TagDTO updateTag(Long id, TagDTO tag);
    TagDTO getTag(Long id);
    Set<TagDTO> getAllTags();
    void deleteTag(Long id);
}
