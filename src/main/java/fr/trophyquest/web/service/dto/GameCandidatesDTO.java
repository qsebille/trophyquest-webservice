package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GameCandidatesDTO(
        UUID gameId,
        String gameTitle,
        String gameImageUrl,
        List<CandidateDTO> candidates
) {
}
