package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.UUID;

public interface TrophyProjection {
    UUID getId();

    int getRank();

    String getTrophyTitle();

    String getTrophyDescription();

    String getTrophyType();

    Boolean getIsHidden();

    String getIconUrl();

    String getGameTitle();

    String getGameGroup();

    Instant getEarnedAt();
}
