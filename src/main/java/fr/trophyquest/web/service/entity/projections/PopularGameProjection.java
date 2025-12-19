package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;
import java.util.UUID;

public interface PopularGameProjection {
    UUID getId();

    String getTitle();

    Optional<String> getAwsImageUrl();

    String getImageUrl();

    long getPlayersCount();
}
