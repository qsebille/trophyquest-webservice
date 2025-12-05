package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record PlayedCollectionDTO(
        UUID id,
        String title,
        String platform,
        String imageUrl
) {
}
