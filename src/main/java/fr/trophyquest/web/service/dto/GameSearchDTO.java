package fr.trophyquest.web.service.dto;

import java.util.List;

public record GameSearchDTO(
        List<GameDTO> content,
        long totalElements
) {
}
