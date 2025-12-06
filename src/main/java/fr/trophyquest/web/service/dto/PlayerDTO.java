package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerDTO(
        UUID id,
        String pseudo,
        String avatarUrl
) {
}
