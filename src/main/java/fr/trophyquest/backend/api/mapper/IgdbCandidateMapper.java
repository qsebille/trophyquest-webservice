package fr.trophyquest.backend.api.mapper;

import fr.trophyquest.backend.api.dto.candidate.IgdbCandidateDTO;
import fr.trophyquest.backend.domain.entity.IgdbCandidate;
import org.springframework.stereotype.Component;

@Component
public class IgdbCandidateMapper {
    public IgdbCandidateDTO toDTO(IgdbCandidate igdbCandidate) {
        return IgdbCandidateDTO.builder()
                .id(igdbCandidate.getId().getCandidateId())
                .name(igdbCandidate.getCandidate().getName())
                .cover(igdbCandidate.getCandidate().getCover())
                .releaseDate(igdbCandidate.getCandidate().getReleaseDate())
                .score(igdbCandidate.getScore())
                .build();
    }
}
