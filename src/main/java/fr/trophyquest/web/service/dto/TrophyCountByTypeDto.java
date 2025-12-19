package fr.trophyquest.web.service.dto;

import lombok.Builder;

@Builder
public record TrophyCountByTypeDto(
        int platinum,
        int gold,
        int silver,
        int bronze
) {
}
