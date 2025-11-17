package fr.trophyquest.web.service.dto;

import java.util.List;
import java.util.UUID;

public record GameDTO(
        UUID gameId,
        String gameName,
        String category,
        String imageUrl,
        List<GameTrophySetDTO> trophySets
) {
}
