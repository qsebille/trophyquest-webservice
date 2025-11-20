package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.Game;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GameMapper {

    public GameDTO toDTO(Game game) {
        return new GameDTO(
                game.getId(),
                game.getTitle(),
                Set.of(game.getPlatforms().split(",")),
                game.getImageUrl()
        );
    }

}
