package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.CollectionDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.CollectionProjection;
import org.springframework.stereotype.Component;

@Component
public class CollectionMapper {

    public CollectionDTO toDTO(CollectionProjection projection) {
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
                .imageUrl(projection.getCollectionImageUrl())
                .gameId(projection.getGameId())
                .gameTitle(projection.getGameTitle())
                .gameImageUrl(projection.getGameImageUrl())
                .trophyCount(trophyCount)
                .build();
    }

}
