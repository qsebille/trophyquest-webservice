package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ObtainedTrophyProjection {
    UUID getId();

    String getTrophyTitle();

    String getTrophyType();

    String getTrophyDescription();

    Optional<String> getTrophyAwsIconUrl();

    String getTrophyIconUrl();

    String getGameTitle();

    String getPlayerId();

    String getPlayerPseudo();

    Optional<String> getPlayerAwsAvatarUrl();

    String getPlayerAvatarUrl();

    Instant getObtainedAt();
}
