package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toDTO(Player player) {
        return new PlayerDTO(
                player.getId(),
                player.getPseudo(),
                player.getAvatarUrl()
        );
    }

}
