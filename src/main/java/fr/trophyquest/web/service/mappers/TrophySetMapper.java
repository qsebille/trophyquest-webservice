package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.IgdbCandidateDTO;
import fr.trophyquest.web.service.dto.TrophySetDTO;
import fr.trophyquest.web.service.entity.Game;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TrophySetMapper {
    private final IgdbCandidateMapper igdbCandidateMapper;

    public TrophySetMapper(IgdbCandidateMapper igdbCandidateMapper) {
        this.igdbCandidateMapper = igdbCandidateMapper;
    }

    public TrophySetDTO toDTO(Game trophySet) {
        Set<IgdbCandidateDTO> igdbCandidates = trophySet.getIgdbCandidates().stream().map(
                this.igdbCandidateMapper::toDTO).collect(Collectors.toSet());

        return TrophySetDTO.builder()
                .id(trophySet.getId())
                .title(trophySet.getTitle())
                .imageUrl(trophySet.getSanitizedImageUrl())
                .platform(trophySet.getPlatform())
                .igdbGameCandidates(igdbCandidates)
                .build();
    }
}
