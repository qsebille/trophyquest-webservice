package fr.trophyquest.web.service.dto;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String imageUrl,
        List<GameDTO> games,
        List<TrophyDTO> trophies
) {
}
