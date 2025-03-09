package ru.serega.musicreviews.services.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serega.musicreviews.dtos.AlbumRequestDTO;
import ru.serega.musicreviews.dtos.ReviewRequestDTO;
import ru.serega.musicreviews.dtos.ReviewResponseDTO;
import ru.serega.musicreviews.entities.Album;
import ru.serega.musicreviews.entities.Review;
import ru.serega.musicreviews.entities.User;
import ru.serega.musicreviews.repos.AlbumRepo;
import ru.serega.musicreviews.repos.ReviewRepo;
import ru.serega.musicreviews.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final AlbumRepo albumRepo;
    private final UserRepo userRepo;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO review) {
        Album album = albumRepo.findById(review.getAlbumId()).orElseThrow();
        User user = userRepo.findByUsername(review.getUsername()).orElseThrow();
        Review newReview = new Review();
        newReview.setAlbum(album);
        newReview.setUser(user);
        newReview.setText(review.getText());
        newReview.setRating(review.getRating());
        Review saved = reviewRepo.save(newReview);
        return new ReviewResponseDTO(saved);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO review) {
        Optional<Review> optional = reviewRepo.findById(id);
        Album album = albumRepo.findById(review.getAlbumId()).orElseThrow();
        User user = userRepo.findByUsername(review.getUsername()).orElseThrow();
        if (optional.isPresent()) {
            Review reviewToUpdate = optional.get();
            reviewToUpdate.setUser(user);
            reviewToUpdate.setAlbum(album);
            reviewToUpdate.setText(review.getText());
            reviewToUpdate.setRating(review.getRating());
            Review updated = reviewRepo.save(reviewToUpdate);
            return new ReviewResponseDTO(updated);
        }
        return null;
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        List<ReviewResponseDTO> reviewResponses = new ArrayList<>();
        reviews.forEach(review -> reviewResponses.add(new ReviewResponseDTO(review)));
        return reviewResponses;
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsByAlbum(AlbumRequestDTO album) {
        List<Review> reviews = reviewRepo.findReviewsByAlbumId(album.getAlbumId());
        return convertToReviewDTOList(reviews);
    }

    @Override
    public ReviewResponseDTO getReview(Long id) {
        Optional<Review> find = reviewRepo.findById(id);
        return find.map(ReviewResponseDTO::new).orElse(null);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsByUser(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        List<Review> reviews = reviewRepo.findReviewsByUser(user);
        return convertToReviewDTOList(reviews);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }

    private List<ReviewResponseDTO> convertToReviewDTOList(List<Review> reviews) {
        return reviews.stream().map(ReviewResponseDTO::new).collect(Collectors.toList());
    }
}
