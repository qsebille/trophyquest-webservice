package fr.trophyquest.web.service.dto;

public record TrophyCountDTO(
        int platinum,
        int gold,
        int silver,
        int bronze
) {
}
