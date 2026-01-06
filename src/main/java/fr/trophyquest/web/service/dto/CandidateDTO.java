package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record CandidateDTO(
        Long id,
        String name,
        String cover,
        Date releaseDate
) {
}
