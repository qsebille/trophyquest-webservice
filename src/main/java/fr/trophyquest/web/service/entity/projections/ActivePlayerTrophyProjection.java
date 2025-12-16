package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ActivePlayerTrophyProjection {
    UUID getPlayerId();

    String getPlayerPseudo();

    Optional<String> getPlayerAwsAvatarUrl();

    String getPlayerAvatarUrl();

    int getTrophyCount();

    UUID getTrophyId();

    String getTrophyTitle();

    String getTrophyDescription();

    String getTrophyType();

    Optional<String> getTrophyAwsIconUrl();

    String getTrophyIconUrl();

    String getGameTitle();

    Instant getObtainedAt();
}
