package fr.trophyquest.backend.api.mapper;

import fr.trophyquest.backend.api.dto.trophysuite.RecentTrophySuiteDTO;
import fr.trophyquest.backend.api.dto.trophysuite.TrophySuiteDTO;
import fr.trophyquest.backend.domain.entity.TrophySuite;
import fr.trophyquest.backend.domain.projection.RecentTrophySuiteRow;
import org.springframework.stereotype.Component;

@Component
public class TrophySuiteMapper {

    public TrophySuiteDTO toDTO(TrophySuite trophySuite) {
        return TrophySuiteDTO.builder()
                .id(trophySuite.getId())
                .title(trophySuite.getName())
                .image(trophySuite.getImage())
                .build();
    }

    public RecentTrophySuiteDTO toRecent(RecentTrophySuiteRow trophySuite) {
        return RecentTrophySuiteDTO.builder()
                .id(trophySuite.getId())
                .title(trophySuite.getTitle())
                .image(trophySuite.getImage())
                .recentPlayers(trophySuite.getRecentPlayers())
                .build();
    }
}
