package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record TrophyDTO(
        UUID id,
        String name,
        String detail,
        String trophyType,
        Boolean isHidden,
        String iconUrl,
        UUID gameId,
        String gameName,
        String gameGroup
) {
}
