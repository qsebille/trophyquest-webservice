package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.dto.UserGameDTO;
import fr.trophyquest.web.service.entity.projections.UserGameProjection;
import org.springframework.stereotype.Component;

@Component
public class UserGameMapper {

    public UserGameDTO toDTO(UserGameProjection projection) {
        return new UserGameDTO(
                projection.getId(),
                projection.getTitle(),
                projection.getImageUrl(),
                projection.getPlatform(),
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
                )
        );
    }

}
