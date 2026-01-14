package fr.trophyquest.backend.api.dto.trophyset;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TrophySetDTO(
        UUID id,
        String title,
        String platform,
        String image
) {
}
