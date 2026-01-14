package fr.trophyquest.backend.api.dto.player;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PlayerSearchItemDTO(
        UUID id,
        String pseudo,
        String avatar,

        UUID lastPlayedTrophySetId,
        String lastPlayedTrophySetTitle,
        String lastPlayedTrophySetPlatform,
        String lastPlayedTrophySetImage,
        Instant lastPlayedTrophySetDate,

        UUID lastEarnedTrophyId,
        String lastEarnedTrophyTitle,
        String lastEarnedTrophyType,
        String lastEarnedTrophyIcon,
        UUID lastEarnedTrophyTrophySetId,
        String lastEarnedTrophyTrophySetTitle,
        Instant lastEarnedTrophyDate,

        long totalPlayedTrophySets,

        long totalEarnedPlatinum,
        long totalEarnedGold,
        long totalEarnedSilver,
        long totalEarnedBronze
) {
}
