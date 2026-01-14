package fr.trophyquest.backend.api.mapper;

import fr.trophyquest.backend.api.dto.trophy.TrophyDTO;
import fr.trophyquest.backend.domain.entity.Trophy;
import org.springframework.stereotype.Component;

@Component
public class TrophyMapper {

    public TrophyDTO toDTO(Trophy trophy) {
        return TrophyDTO.builder()
                .id(trophy.getId())
                .rank(trophy.getRank())
                .title(trophy.getTitle())
                .description(trophy.getDescription())
                .trophyType(trophy.getTrophyType())
                .isHidden(trophy.isHidden())
                .iconUrl(trophy.getIconUrl())
                .gameGroup(trophy.getGameGroupId())
                .build();
    }

}
