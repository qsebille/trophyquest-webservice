package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;

public interface CollectionProjection {
    String getCollectionId();

    String getCollectionTitle();

    String getCollectionPlatform();

    String getCollectionImageUrl();

    Optional<String> getCollectionAwsImageUrl();

    String getGameId();

    String getGameTitle();

    String getGameImageUrl();

    Optional<String> getGameAwsImageUrl();

    int getPlatinumCount();

    int getGoldCount();

    int getSilverCount();

    int getBronzeCount();
}
