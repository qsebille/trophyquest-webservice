package fr.trophyquest.backend.api.dto.game;

import fr.trophyquest.backend.api.dto.candidate.IgdbCandidateDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record GameWithIgdbCandidatesDTO(
        GameDTO game,
        List<IgdbCandidateDTO> candidates
) {
}
