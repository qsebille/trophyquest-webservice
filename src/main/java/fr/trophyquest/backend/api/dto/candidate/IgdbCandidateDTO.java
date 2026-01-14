package fr.trophyquest.backend.api.dto.candidate;

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
