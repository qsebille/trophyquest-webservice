package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerGameDTO(
        UUID id,
        String title,
        String platform,
        String imageUrl,
        TrophyCountByTypeDto totalTrophies,
        TrophyCountByTypeDto earnedTrophies
) {
}
