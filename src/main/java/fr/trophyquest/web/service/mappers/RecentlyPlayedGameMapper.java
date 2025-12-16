package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.RecentlyPlayedGameDTO;
import fr.trophyquest.web.service.entity.projections.RecentlyPlayedGameProjection;
import org.springframework.stereotype.Component;

@Component
public class RecentlyPlayedGameMapper {

    public RecentlyPlayedGameDTO toDTO(RecentlyPlayedGameProjection projection) {
        String imageUrl = projection.getAwsImageUrl().orElse(projection.getImageUrl());

        return RecentlyPlayedGameDTO.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .imageUrl(imageUrl)
                .recentPlayers(projection.getPlayersCount())
                .build();
    }

}
