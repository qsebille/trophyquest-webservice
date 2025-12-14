package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GameDTO(
        UUID id,
        String title,
        String imageUrl
) {
}
