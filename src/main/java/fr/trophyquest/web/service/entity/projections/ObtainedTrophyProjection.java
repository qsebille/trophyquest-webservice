package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.UUID;

public interface ObtainedTrophyProjection {
    UUID getId();

    String getTrophyTitle();

    String getTrophyType();

    String getTrophyDescription();

    String getTrophyIconUrl();

    String getGameTitle();

    String getPlayerId();

    String getPlayerPseudo();

    String getPlayerAvatarUrl();

    Instant getObtainedAt();
}
