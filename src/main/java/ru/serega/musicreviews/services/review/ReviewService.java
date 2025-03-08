package ru.serega.musicreviews.services.review;

import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.ReviewRequestDTO;
import ru.serega.musicreviews.dtos.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO review);
    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO review);
    List<ReviewResponseDTO> getAllReviews();
    List<ReviewResponseDTO> getAllReviewsByAlbum(AlbumRequestDTO album);
    ReviewResponseDTO getReview(Long id);
    List<ReviewResponseDTO> getAllReviewsByUser(String user);
    void deleteReview(Long id);
}
