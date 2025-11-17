package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.PsnTitle;
import org.springframework.stereotype.Component;

@Component
public class GameDTOMapper {

    public GameDTO toDTO(PsnTitle entity) {
        return new GameDTO(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl()
        );
    }

}