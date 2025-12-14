package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PlayerCollectionDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.PlayerCollectionProjection;
import org.springframework.stereotype.Component;

@Component
public class PlayerCollectionMapper {

    public PlayerCollectionDTO toDTO(PlayerCollectionProjection projection) {
        String imageUrl = projection.getCollectionAwsImageUrl().orElse(projection.getCollectionImageUrl());

        TrophyCountDTO collectionTrophies = TrophyCountDTO.builder()
                .platinum(projection.getPlatinumCount())
                .gold(projection.getGoldCount())
                .silver(projection.getSilverCount())
                .bronze(projection.getBronzeCount())
                .build();

        TrophyCountDTO earnedTrophies = TrophyCountDTO.builder()
                .platinum(projection.getEarnedPlatinumCount())
                .gold(projection.getEarnedGoldCount())
                .silver(projection.getEarnedSilverCount())
                .bronze(projection.getEarnedBronzeCount())
                .build();

        return PlayerCollectionDTO.builder()
                .collectionId(projection.getCollectionId())
                .collectionTitle(projection.getCollectionTitle())
                .collectionPlatform(projection.getCollectionPlatform())
                .collectionImageUrl(imageUrl)
                .gameId(projection.getGameId())
                .gameTitle(projection.getGameTitle())
                .collectionTrophies(collectionTrophies)
                .earnedTrophies(earnedTrophies)
                .build();
    }

}
