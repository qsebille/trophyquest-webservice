package fr.trophyquest.web.service.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ObtainedTrophyDTO(
        UUID id,
        String trophyTitle,
        String trophyType,
        String trophyDescription,
        String trophyIconUrl,
        String gameTitle,
        ZonedDateTime obtainedDate,
        String obtainedBy
) {
}
