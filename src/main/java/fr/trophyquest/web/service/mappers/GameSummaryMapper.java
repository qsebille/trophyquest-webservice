package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.GameSummaryDTO;
import fr.trophyquest.web.service.dto.TrophyCountByTypeDto;
import fr.trophyquest.web.service.entity.projections.GameSummaryProjection;
import org.springframework.stereotype.Component;

@Component
public class GameSummaryMapper {

    public GameSummaryDTO toDTO(GameSummaryProjection projection) {
        String imageUrl = projection.getAwsImageUrl().orElse(projection.getImageUrl());

        TrophyCountByTypeDto trophyCount = TrophyCountByTypeDto.builder()
                .platinum(projection.getPlatinumCount())
                .gold(projection.getGoldCount())
                .silver(projection.getSilverCount())
                .bronze(projection.getBronzeCount())
                .build();

        return GameSummaryDTO.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .platform(projection.getPlatform())
                .imageUrl(imageUrl)
                .trophyCount(trophyCount)
                .build();
    }

}
