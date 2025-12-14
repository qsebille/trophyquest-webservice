package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.ObtainedTrophyDTO;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.entity.projections.ObtainedTrophyProjection;
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
            ZoneId zoneId = ZoneId.of("Europe/Paris");
            ZonedDateTime zonedEarnedAt = earnedAt.atZone(zoneId);
            return Optional.of(zonedEarnedAt);
        }
    }

    public TrophyDTO toTrophyDTO(TrophyProjection projection) {
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

    public ObtainedTrophyDTO toObtainedDTO(ObtainedTrophyProjection projection) {
        String avatarUrl = projection.getPlayerAwsAvatarUrl().orElse(projection.getPlayerAvatarUrl());
        String iconUrl = projection.getTrophyAwsIconUrl().orElse(projection.getTrophyIconUrl());

        return ObtainedTrophyDTO.builder()
                .id(projection.getId())
                .trophyTitle(projection.getTrophyTitle())
                .trophyType(projection.getTrophyType())
                .trophyDescription(projection.getTrophyDescription())
                .trophyIconUrl(iconUrl)
                .gameTitle(projection.getGameTitle())
                .playerId(projection.getPlayerId())
                .playerPseudo(projection.getPlayerPseudo())
                .playerAvatarUrl(avatarUrl)
                .obtainedDate(projection.getObtainedAt().atZone(ZONE_ID))
                .build();
    }

}
