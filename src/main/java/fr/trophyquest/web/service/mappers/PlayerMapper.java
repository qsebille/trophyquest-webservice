package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toDTO(Player player) {
        String avatarUrl = player.getAwsAvatarUrl();
        if (null == avatarUrl) avatarUrl = player.getAvatarUrl();

        return PlayerDTO.builder()
                .id(player.getId())
                .pseudo(player.getPseudo())
                .avatarUrl(avatarUrl)
                .build();
    }

}
