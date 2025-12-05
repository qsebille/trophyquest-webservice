package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDTO toDTO(Game game) {
        return new GameDTO(
                game.getId(),
                game.getTitle(),
                game.getImageUrl()
        );
    }

}
