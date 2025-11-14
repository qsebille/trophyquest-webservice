package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.UserDTO;
import fr.trophyquest.web.service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    private final GameDTOMapper gameDTOMapper = new GameDTOMapper();
    private final TrophyDTOMapper trophyDTOMapper = new TrophyDTOMapper();

    public UserDTO toDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getAvatarUrl(),
                entity.getGames().stream().map(gameDTOMapper::toDTO).toList(),
                entity.getTrophies().stream().map(trophyDTOMapper::toDTO).toList()
        );
    }

}