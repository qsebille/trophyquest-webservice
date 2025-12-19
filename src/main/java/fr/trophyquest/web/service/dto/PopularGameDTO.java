package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PopularGameDTO(
        UUID id,
        String title,
        String imageUrl,
        long recentPlayers
) {
}
