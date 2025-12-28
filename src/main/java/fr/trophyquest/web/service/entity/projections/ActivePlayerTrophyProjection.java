package fr.trophyquest.web.service.entity.projections;

import java.util.UUID;

public record ActivePlayerTrophyProjection(
        UUID id,
        String title,
        String description,
        String trophyType,
        String awsIconUrl,
        String iconUrl,
        String obtainedAt,
        UUID gameId,
        String gameTitle
) {
}
