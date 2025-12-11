package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Builder
public record TrophyDTO(
        UUID id,
        int rank,
        String trophyTitle,
        String trophyDescription,
        String trophyType,
        Boolean isHidden,
        String iconUrl,
        String gameTitle,
        String gameGroup,
        Optional<ZonedDateTime> earnedDate
) {
}
