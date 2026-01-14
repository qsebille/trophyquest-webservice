package fr.trophyquest.backend.api.dto.trophyset;

import fr.trophyquest.backend.api.dto.candidate.IgdbCandidateDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record TrophySetWithCandidatesDTO(
        TrophySetDTO trophySet,
        List<IgdbCandidateDTO> mappingCandidates
) {
}
