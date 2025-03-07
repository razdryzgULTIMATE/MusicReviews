package ru.serega.musicreviews.dtos;

import lombok.Data;

public record AuthenticationResponseDTO(String accessToken, String refreshToken) {
}
