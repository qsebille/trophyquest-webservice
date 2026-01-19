package fr.trophyquest.backend.api.dto.trophysuite;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PlayedTrophySuiteSearchItemDTO(
        UUID id,
        String title,
        String platform,
        String image,
        Instant lastPlayedAt,

        long totalTrophies,
        long totalEarnedPlatinum,
        long totalEarnedGold,
        long totalEarnedSilver,
        long totalEarnedBronze
) {
}
