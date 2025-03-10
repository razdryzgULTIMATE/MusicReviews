package ru.serega.musicreviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.ReviewRequestDTO;
import ru.serega.musicreviews.services.review.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user/review")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDTO review){
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @PutMapping("/user/review/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDTO review){
        return ResponseEntity.ofNullable(reviewService.updateReview(reviewId, review));
    }

    @GetMapping("/review/all")
    public ResponseEntity<?> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId){
        return ResponseEntity.ofNullable(reviewService.getReview(reviewId));
    }

    @GetMapping("/review/by-user")
    public ResponseEntity<?> getReviewsByUser(@RequestParam String username){
        return ResponseEntity.ok(reviewService.getAllReviewsByUser(username));
    }

    @GetMapping("/review/by-album")
    public ResponseEntity<?> getReviewsByAlbum(@RequestBody AlbumRequestDTO album){
        return ResponseEntity.ok(reviewService.getAllReviewsByAlbum(album));
    }

    @DeleteMapping("/user/review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

}
