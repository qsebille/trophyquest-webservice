package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record PsnUserDTO(
        UUID id,
        String name,
        String imageUrl
) {
}
