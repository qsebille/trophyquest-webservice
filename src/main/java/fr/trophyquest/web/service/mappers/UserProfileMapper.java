package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.UserProfileDTO;
import fr.trophyquest.web.service.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileDTO toDTO(UserProfile userProfile) {
        return new UserProfileDTO(
                userProfile.getId(),
                userProfile.getName(),
                userProfile.getAvatarUrl()
        );
    }

}
