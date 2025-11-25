package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.UUID;

public interface EarnedTrophyProjection {
    UUID getId();

    String getTrophyTitle();

    String getTrophyDescription();

    String getTrophyType();

    String getIconUrl();

    String getGameTitle();

    Instant getEarnedAt();
}
