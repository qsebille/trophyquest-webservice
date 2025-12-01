package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.entity.projections.TrophyProjection;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class TrophyMapper {

    private Optional<ZonedDateTime> toZonedDateTime(Instant earnedAt) {
        if (null == earnedAt) {
            return Optional.empty();
        } else {
            ZoneId zoneId = ZoneId.of("Europe/Paris");
            ZonedDateTime zonedEarnedAt = earnedAt.atZone(zoneId);
            return Optional.of(zonedEarnedAt);
        }
    }

    public TrophyDTO toTrophyDTO(TrophyProjection projection) {
        return new TrophyDTO(
                projection.getId(),
                projection.getRank(),
                projection.getTrophyTitle(),
                projection.getTrophyDescription(),
                projection.getTrophyType(),
                projection.getIsHidden(),
                projection.getIconUrl(),
                projection.getGameTitle(),
                projection.getGameGroup(),
                this.toZonedDateTime(projection.getEarnedAt())
        );
    }

}
