package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record PlayerDTO(
        UUID id,
        String pseudo,
        String avatarUrl
) {
}
