package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.UUID;

public interface UserTrophyProjection {
    UUID getId();

    int getRank();

    String getName();

    String getDescription();

    String getTrophyType();

    Boolean getIsHidden();

    String getIconUrl();

    String getGameGroup();

    Instant getEarnedAt();
}