package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PopularGameDTO;
import fr.trophyquest.web.service.entity.projections.PopularGameProjection;
import org.springframework.stereotype.Component;

@Component
public class PopularGameMapper {

    public PopularGameDTO toDTO(PopularGameProjection projection) {
        String imageUrl = projection.getAwsImageUrl().orElse(projection.getImageUrl());

        return PopularGameDTO.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .imageUrl(imageUrl)
                .recentPlayers(projection.getPlayersCount())
                .build();
    }

}
