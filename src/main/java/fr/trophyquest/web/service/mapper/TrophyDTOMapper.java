package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.entity.PsnTrophy;
import org.springframework.stereotype.Component;

@Component
public class TrophyDTOMapper {

    public TrophyDTO toDTO(PsnTrophy entity) {
        return new TrophyDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTrophyType(),
                entity.getIsHidden(),
                entity.getIconUrl(),
                entity.getTrophySet().getName(),
                entity.getGameGroupId(),
                null,
                entity.getQuickGuide(),
                entity.getYoutubeVideoUrl(),
                entity.getYoutubeThumbnailUrl()
        );
    }

}