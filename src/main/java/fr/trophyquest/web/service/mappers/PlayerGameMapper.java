package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PlayerGameDTO;
import fr.trophyquest.web.service.dto.TrophyCountByTypeDto;
import fr.trophyquest.web.service.entity.projections.PlayerGameProjection;
import org.springframework.stereotype.Component;

@Component
public class PlayerGameMapper {

    public PlayerGameDTO toDTO(PlayerGameProjection projection) {
        String imageUrl = projection.getAwsImageUrl().orElse(projection.getImageUrl());

        TrophyCountByTypeDto totalTrophies = TrophyCountByTypeDto.builder()
                .platinum(projection.getPlatinumCount())
                .gold(projection.getGoldCount())
                .silver(projection.getSilverCount())
                .bronze(projection.getBronzeCount())
                .build();

        TrophyCountByTypeDto earnedTrophies = TrophyCountByTypeDto.builder()
                .platinum(projection.getEarnedPlatinumCount())
                .gold(projection.getEarnedGoldCount())
                .silver(projection.getEarnedSilverCount())
                .bronze(projection.getEarnedBronzeCount())
                .build();

        return PlayerGameDTO.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .platform(projection.getPlatform())
                .imageUrl(imageUrl)
                .totalTrophies(totalTrophies)
                .earnedTrophies(earnedTrophies)
                .build();
    }

}
