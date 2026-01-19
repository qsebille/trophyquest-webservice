package fr.trophyquest.backend.api.dto.player;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PlayerSearchItemDTO(
        UUID id,
        String pseudo,
        String avatar,

        UUID lastPlayedTrophySuiteId,
        String lastPlayedTrophySuiteTitle,
        String lastPlayedTrophySuitePlatform,
        String lastPlayedTrophySuiteImage,
        Instant lastPlayedTrophySuiteDate,

        UUID lastEarnedTrophyId,
        String lastEarnedTrophyTitle,
        String lastEarnedTrophyType,
        String lastEarnedTrophyIcon,
        UUID lastEarnedTrophyTrophySuiteId,
        String lastEarnedTrophyTrophySuiteTitle,
        Instant lastEarnedTrophyDate,

        long totalPlayedTrophySuites,

        long totalEarnedPlatinum,
        long totalEarnedGold,
        long totalEarnedSilver,
        long totalEarnedBronze
) {
}
