package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.IgdbCandidateDTO;
import fr.trophyquest.web.service.dto.TrophySetDTO;
import fr.trophyquest.web.service.entity.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class TrophySetMapper {
    private final IgdbCandidateMapper igdbCandidateMapper;

    public TrophySetMapper(IgdbCandidateMapper igdbCandidateMapper) {
        this.igdbCandidateMapper = igdbCandidateMapper;
    }

    public TrophySetDTO toDTO(Game trophySet) {
        List<IgdbCandidateDTO> igdbCandidates = new ArrayList<>(trophySet.getIgdbCandidates().stream().map(
                this.igdbCandidateMapper::toDTO).toList());

        igdbCandidates.sort(
                Comparator.comparing(IgdbCandidateDTO::score, Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed());

        return TrophySetDTO.builder()
                .id(trophySet.getId())
                .title(trophySet.getTitle())
                .imageUrl(trophySet.getSanitizedImageUrl())
                .platform(trophySet.getPlatform())
                .igdbGameCandidates(igdbCandidates)
                .build();
    }
}
