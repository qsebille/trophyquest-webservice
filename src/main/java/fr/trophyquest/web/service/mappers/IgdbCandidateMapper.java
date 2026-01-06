package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.IgdbCandidateDTO;
import fr.trophyquest.web.service.entity.IgdbCandidate;
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
