package fr.trophyquest.backend.api.dto.player;

import lombok.Builder;

@Builder
public record PlayerStatsDTO(
        long totalTrophySetsPlayed,
        long totalPlatinumTrophies,
        long totalGoldTrophies,
        long totalSilverTrophies,
        long totalBronzeTrophies
) {
}
