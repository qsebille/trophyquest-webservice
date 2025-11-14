package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameDTOMapper {

    private final TrophyDTOMapper trophyDTOMapper = new TrophyDTOMapper();

    public GameDTO toDTO(Game entity) {
        return new GameDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getPlatform(),
                entity.getImageUrl()
        );
    }

}