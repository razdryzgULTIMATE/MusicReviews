package ru.serega.musicreviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serega.musicreviews.dtos.TagDTO;
import ru.serega.musicreviews.services.tag.TagService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/tag")
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tag) {
        return ResponseEntity.ofNullable(tagService.createTag(tag));
    }

    @PutMapping("/tag/{tagId}")
    public ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tag, @PathVariable long tagId) {
        return ResponseEntity.ofNullable(tagService.updateTag(tagId, tag));
    }

    @GetMapping("/tag/all")
    public ResponseEntity<Set<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<TagDTO> getTag(@PathVariable long tagId) {
        return ResponseEntity.ofNullable(tagService.getTag(tagId));
    }

    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<TagDTO> deleteTag(@PathVariable long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }


}
