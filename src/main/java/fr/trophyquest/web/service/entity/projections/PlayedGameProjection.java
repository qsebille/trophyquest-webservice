package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.UUID;

public interface PlayedGameProjection {
    UUID getId();

    String getName();

    String getPlatform();

    String getImageUrl();

    Instant getLastPlayedDate();
}