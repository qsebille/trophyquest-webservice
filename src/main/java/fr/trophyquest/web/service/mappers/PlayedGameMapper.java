package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PlayedCollectionDTO;
import fr.trophyquest.web.service.dto.PlayedGameDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.TrophyCollection;
import fr.trophyquest.web.service.entity.projections.PlayedGameProjection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class PlayedGameMapper {

    public List<PlayedGameDTO> toDtos(
            List<PlayedGameProjection> projections,
            Map<UUID, Set<TrophyCollection>> collectionMap
    ) {
        return projections.stream().map(projection -> {
            UUID gameId = projection.getId();
            Set<PlayedCollectionDTO> mappedCollections = new HashSet<>();
            Set<TrophyCollection> trophyCollections = collectionMap.getOrDefault(gameId, Set.of());
            if (!trophyCollections.isEmpty()) {
                collectionMap.get(gameId).forEach(tc -> mappedCollections.add(new PlayedCollectionDTO(
                        tc.getId(),
                        tc.getTitle(),
                        tc.getPlatform(),
                        tc.getImageUrl()
                )));
            }

            return new PlayedGameDTO(
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
