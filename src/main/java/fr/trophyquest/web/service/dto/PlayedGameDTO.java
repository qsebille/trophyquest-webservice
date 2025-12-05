package fr.trophyquest.web.service.dto;

import java.util.Set;
import java.util.UUID;

public record PlayedGameDTO(
        UUID id,
        String title,
        String imageUrl,
        TrophyCountDTO totalTrophies,
        TrophyCountDTO earnedTrophies,
        Set<PlayedCollectionDTO> trophyCollections
) {
}
