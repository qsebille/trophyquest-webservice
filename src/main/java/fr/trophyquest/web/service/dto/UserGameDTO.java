package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record UserGameDTO(
        UUID id,
        String title,
        String imageUrl,
        String platform,
        TrophyCountDTO totalTrophies,
        TrophyCountDTO earnedTrophies
) {
}
