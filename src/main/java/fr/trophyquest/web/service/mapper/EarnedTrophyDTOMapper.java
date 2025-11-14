package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.EarnedTrophyDTO;
import fr.trophyquest.web.service.entity.projections.EarnedTrophyProjection;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class EarnedTrophyDTOMapper {

    public EarnedTrophyDTO toDTO(EarnedTrophyProjection projection) {
        return new EarnedTrophyDTO(projection.getTrophyId(), projection.getTrophyTitle(), projection.getTrophyDetail(), projection.getTrophyType(), projection.getEarnedDate().atZone(ZoneId.of("Europe/Paris")));
    }

}