package fr.trophyquest.web.service.dto;

import lombok.Builder;

@Builder
public record PlayerCollectionDTO(
        String collectionId,
        String collectionTitle,
        String collectionPlatform,
        String collectionImageUrl,
        String gameId,
        String gameTitle,
        String gameImageUrl,
        TrophyCountDTO collectionTrophies,
        TrophyCountDTO earnedTrophies
) {
}
