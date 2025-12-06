package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerSummaryDTO(
        PlayerDTO player,
        TrophyCountDTO trophyCount,
        int totalGamesPlayed,
        UUID lastPlayedCollectionId,
        UUID lastPlayedGameId,
        String lastPlayedGameTitle,
        String lastPlayedGameImageUrl
) {
}
