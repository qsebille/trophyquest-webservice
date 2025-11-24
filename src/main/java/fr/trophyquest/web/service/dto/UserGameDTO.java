package fr.trophyquest.web.service.dto;

import java.util.Set;
import java.util.UUID;

public record UserGameDTO(
        UUID id,
        String title,
        String imageUrl,
        TrophyCountDTO totalTrophies,
        TrophyCountDTO earnedTrophies,
        Set<UUID> trophyCollections
) {
}
