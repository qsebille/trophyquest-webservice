package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDTO toDTO(Game game) {
        String imageUrl = game.getAwsImageUrl();
        if (null == imageUrl) imageUrl = game.getImageUrl();

        return GameDTO.builder()
                .id(game.getId())
                .title(game.getTitle())
                .imageUrl(imageUrl)
                .build();
    }

}
