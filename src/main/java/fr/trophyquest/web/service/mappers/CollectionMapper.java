package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.CollectionDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.CollectionProjection;
import org.springframework.stereotype.Component;

@Component
public class CollectionMapper {

    public CollectionDTO toDTO(CollectionProjection projection) {
        String collectionImageUrl = projection.getCollectionAwsImageUrl().orElse(projection.getCollectionImageUrl());
        String gameImageUrl = projection.getGameAwsImageUrl().orElse(projection.getGameImageUrl());

        TrophyCountDTO trophyCount = TrophyCountDTO.builder()
                .platinum(projection.getPlatinumCount())
                .gold(projection.getGoldCount())
                .silver(projection.getSilverCount())
                .bronze(projection.getBronzeCount())
                .build();

        return CollectionDTO.builder()
                .id(projection.getCollectionId())
                .title(projection.getCollectionTitle())
                .platform(projection.getCollectionPlatform())
                .imageUrl(collectionImageUrl)
                .gameId(projection.getGameId())
                .gameTitle(projection.getGameTitle())
                .gameImageUrl(gameImageUrl)
                .trophyCount(trophyCount)
                .build();
    }

}
