package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.EarnedTrophyDTO;
import fr.trophyquest.web.service.entity.projections.EarnedTrophyProjection;
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
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedEarnedAt = earnedAt.atZone(zoneId);
            return Optional.of(zonedEarnedAt);
        }
    }

    public EarnedTrophyDTO toEarnedTrophyDTO(EarnedTrophyProjection projection) {
        return new EarnedTrophyDTO(
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
