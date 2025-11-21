package fr.trophyquest.web.service.dto;

import java.util.Set;
import java.util.UUID;

public record GameDTO(
        UUID id,
        String title,
        Set<String> platforms,
        String imageUrl
) {
}
