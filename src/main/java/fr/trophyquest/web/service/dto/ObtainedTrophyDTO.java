package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record ObtainedTrophyDTO(
        UUID id,
        String trophyTitle,
        String trophyType,
        String trophyDescription,
        String trophyIconUrl,
        String gameTitle,
        ZonedDateTime obtainedDate
) {
}
