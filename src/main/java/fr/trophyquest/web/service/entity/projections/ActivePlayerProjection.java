package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;
import java.util.UUID;

public interface ActivePlayerProjection {
    UUID getId();

    String getPseudo();

    Optional<String> getAwsAvatarUrl();

    String getAvatarUrl();

    int getTrophyCount();

    String getTrophiesJson();
}
