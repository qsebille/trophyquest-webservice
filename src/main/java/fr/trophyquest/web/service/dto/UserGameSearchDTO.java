package fr.trophyquest.web.service.dto;

import java.util.List;

public record UserGameSearchDTO(
        List<UserGameDTO> content,
        long totalElements
) {
}
