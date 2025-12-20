package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerSummaryDTO(
        PlayerDTO player,
        TrophyCountByTypeDto trophyCount,
        int totalGamesPlayed,
        UUID lastPlayedGameId,
        String lastPlayedGameTitle,
        String lastPlayedGamePlatform,
        String lastPlayedGameImageUrl
) {
}
