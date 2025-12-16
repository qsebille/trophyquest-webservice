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

    private final static ZoneId ZONE_ID = ZoneId.of("Europe/Paris");

    private Optional<ZonedDateTime> toZonedDateTime(Instant earnedAt) {
        if (null == earnedAt) {
            return Optional.empty();
        } else {
            ZonedDateTime zonedEarnedAt = earnedAt.atZone(ZONE_ID);
            return Optional.of(zonedEarnedAt);
        }
    }

    public TrophyDTO toDTO(TrophyProjection projection) {
        String iconUrl = projection.getAwsIconUrl().orElse(projection.getIconUrl());

        return TrophyDTO.builder()
                .id(projection.getId())
                .rank(projection.getRank())
                .trophyTitle(projection.getTrophyTitle())
                .trophyDescription(projection.getTrophyDescription())
                .trophyType(projection.getTrophyType())
                .isHidden(projection.getIsHidden())
                .iconUrl(iconUrl)
                .gameTitle(projection.getGameTitle())
                .gameGroup(projection.getGameGroup())
                .earnedDate(this.toZonedDateTime(projection.getEarnedAt()))
                .build();
    }

}
