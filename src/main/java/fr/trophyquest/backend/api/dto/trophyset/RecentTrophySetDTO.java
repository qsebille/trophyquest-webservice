package fr.trophyquest.backend.api.dto.trophyset;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RecentTrophySetDTO(
        UUID id,
        String title,
        String image,
        Long recentPlayers
) {
}
