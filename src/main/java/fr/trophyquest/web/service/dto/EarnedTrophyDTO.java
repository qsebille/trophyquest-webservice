package fr.trophyquest.web.service.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record EarnedTrophyDTO(
        UUID id,
        String trophyTitle,
        String trophyDescription,
        String trophyType,
        String iconUrl,
        String gameTitle,
        ZonedDateTime earnedDate
) {
}
