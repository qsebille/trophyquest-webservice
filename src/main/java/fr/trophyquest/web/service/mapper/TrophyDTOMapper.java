package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.model.Trophy;
import org.springframework.stereotype.Component;

@Component
public class TrophyDTOMapper {

    public TrophyDTO toDTO(Trophy entity) {
        return new TrophyDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDetail(),
                entity.getTrophyType(),
                entity.getIsHidden(),
                entity.getIconUrl(),
                entity.getGame().getId(),
                entity.getGame().getTitle(),
                entity.getGameGroup()
        );
    }

}