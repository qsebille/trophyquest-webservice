package fr.trophyquest.web.service.dto;

import lombok.Builder;

@Builder
public record CollectionDTO(
        String id,
        String title,
        String platform,
        String imageUrl,
        String gameId,
        String gameTitle,
        String gameImageUrl,
        TrophyCountDTO trophyCount
) {
}
