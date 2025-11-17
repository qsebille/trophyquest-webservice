package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.PsnUserDTO;
import fr.trophyquest.web.service.entity.PsnUser;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public PsnUserDTO toDTO(PsnUser entity) {
        return new PsnUserDTO(
                entity.getId(),
                entity.getProfileName(),
                entity.getAvatarUrl()
        );
    }

}