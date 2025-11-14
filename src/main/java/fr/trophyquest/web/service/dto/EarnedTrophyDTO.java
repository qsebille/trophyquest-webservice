package fr.trophyquest.web.service.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record EarnedTrophyDTO(
        UUID id,
        String title,
        String detail,
        String trophyType,
        ZonedDateTime earnedTrophyDate
) {
}
