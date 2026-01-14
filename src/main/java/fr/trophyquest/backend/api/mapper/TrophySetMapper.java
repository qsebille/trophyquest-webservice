package fr.trophyquest.backend.api.mapper;

import fr.trophyquest.backend.api.dto.candidate.IgdbCandidateDTO;
import fr.trophyquest.backend.api.dto.trophyset.RecentTrophySetDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetWithCandidatesDTO;
import fr.trophyquest.backend.domain.entity.TrophySet;
import fr.trophyquest.backend.domain.projection.RecentTrophySetRow;
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

    public TrophySetDTO toDTO(TrophySet trophySet) {
        return TrophySetDTO.builder()
                .id(trophySet.getId())
                .title(trophySet.getTitle())
                .image(trophySet.getImage())
                .platform(trophySet.getPlatform())
                .build();
    }

    public TrophySetWithCandidatesDTO toWithCandidatesDTO(TrophySet trophySet) {
        List<IgdbCandidateDTO> igdbCandidates = new ArrayList<>(trophySet.getIgdbCandidates().stream().map(
                this.igdbCandidateMapper::toDTO).toList());
        igdbCandidates.sort(
                Comparator.comparing(IgdbCandidateDTO::score, Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed());

        return TrophySetWithCandidatesDTO.builder()
                .trophySet(this.toDTO(trophySet))
                .mappingCandidates(igdbCandidates)
                .build();
    }

    public RecentTrophySetDTO toRecent(RecentTrophySetRow trophySet) {
        return RecentTrophySetDTO.builder()
                .id(trophySet.getId())
                .title(trophySet.getTitle())
                .image(trophySet.getImage())
                .recentPlayers(trophySet.getRecentPlayers())
                .build();
    }
}
