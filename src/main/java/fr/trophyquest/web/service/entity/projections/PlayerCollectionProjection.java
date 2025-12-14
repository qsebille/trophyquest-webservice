package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;

public interface PlayerCollectionProjection {
    String getCollectionId();

    String getCollectionTitle();

    String getCollectionPlatform();

    Optional<String> getCollectionAwsImageUrl();

    String getCollectionImageUrl();

    String getGameId();

    String getGameTitle();

    int getPlatinumCount();

    int getGoldCount();

    int getSilverCount();

    int getBronzeCount();

    int getEarnedPlatinumCount();

    int getEarnedGoldCount();

    int getEarnedSilverCount();

    int getEarnedBronzeCount();
}
