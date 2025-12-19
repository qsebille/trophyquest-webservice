package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;
import java.util.UUID;

public interface PlayerGameProjection {
    UUID getId();

    String getTitle();

    String getPlatform();

    Optional<String> getAwsImageUrl();

    String getImageUrl();

    int getPlatinumCount();

    int getGoldCount();

    int getSilverCount();

    int getBronzeCount();

    int getEarnedPlatinumCount();

    int getEarnedGoldCount();

    int getEarnedSilverCount();

    int getEarnedBronzeCount();
}
