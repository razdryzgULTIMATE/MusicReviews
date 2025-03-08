package ru.serega.musicreviews.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.serega.musicreviews.entities.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String username;
    private String text;
    private short rating;
    private LocalDateTime reviewDate;

    public ReviewResponseDTO(Review review) {
        this(
                review.getId(),
                review.getUser().getUsername(),
                review.getText(),
                review.getRating(),
                review.getReviewDate()
        );
    }


}
