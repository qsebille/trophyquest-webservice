package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.EarnedTrophyDTO;
import fr.trophyquest.web.service.entity.projections.EarnedTrophyProjection;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class TrophyMapper {

    public EarnedTrophyDTO toEarnedTrophyDTO(EarnedTrophyProjection projection) {
        return new EarnedTrophyDTO(
                projection.getId(),
                projection.getTrophyTitle(),
                projection.getTrophyDescription(),
                projection.getTrophyType(),
                projection.getIconUrl(),
                projection.getGameTitle(),
                projection.getEarnedAt().atZone(ZoneId.of("Europe/Paris"))
        );
    }

}
