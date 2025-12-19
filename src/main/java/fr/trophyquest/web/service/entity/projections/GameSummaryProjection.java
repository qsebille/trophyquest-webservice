package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;
import java.util.UUID;

public interface GameSummaryProjection {
    UUID getId();

    String getTitle();

    String getPlatform();

    Optional<String> getAwsImageUrl();

    String getImageUrl();

    int getPlatinumCount();

    int getGoldCount();

    int getSilverCount();

    int getBronzeCount();
}
