package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.dto.UserGameCollectionDTO;
import fr.trophyquest.web.service.dto.UserGameDTO;
import fr.trophyquest.web.service.entity.TrophyCollection;
import fr.trophyquest.web.service.entity.projections.UserGameProjection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class UserGameMapper {

    public List<UserGameDTO> toDtos(
            List<UserGameProjection> projections,
            Map<UUID, Set<TrophyCollection>> collectionMap
    ) {
        return projections.stream().map(projection -> {
            UUID gameId = projection.getId();
            Set<UserGameCollectionDTO> mappedCollections = new HashSet<>();
            collectionMap.get(gameId).forEach(tc -> mappedCollections.add(new UserGameCollectionDTO(
                    tc.getId(),
                    tc.getTitle(),
                    tc.getPlatform(),
                    tc.getImageUrl()
            )));

            return new UserGameDTO(
                    gameId,
                    projection.getTitle(),
                    projection.getImageUrl(),
                    new TrophyCountDTO(
                            projection.getTotalPlatinum(),
                            projection.getTotalGold(),
                            projection.getTotalSilver(),
                            projection.getTotalBronze()
                    ),
                    new TrophyCountDTO(
                            projection.getEarnedPlatinum(),
                            projection.getEarnedGold(),
                            projection.getEarnedSilver(),
                            projection.getEarnedBronze()
                    ),
                    mappedCollections
            );
        }).toList();
    }

}
