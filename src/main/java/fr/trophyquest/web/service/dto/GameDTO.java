package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record GameDTO(
        UUID id,
        String name,
        String imageUrl
) {
}
