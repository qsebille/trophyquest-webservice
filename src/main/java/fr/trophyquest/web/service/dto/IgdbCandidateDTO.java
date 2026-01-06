package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record IgdbCandidateDTO(
        Long id,
        String name,
        String cover,
        Date releaseDate,
        Long score
) {
}
