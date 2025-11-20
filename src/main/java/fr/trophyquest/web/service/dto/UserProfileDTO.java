package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record UserProfileDTO(
        UUID id,
        String profileName,
        String avatarUrl
) {
}
