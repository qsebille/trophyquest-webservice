package fr.trophyquest.web.service.entity.projections;

public interface PlayerCollectionProjection {
    String getCollectionId();

    String getCollectionTitle();

    String getCollectionPlatform();

    String getCollectionImageUrl();

    String getGameId();

    String getGameTitle();

    String getGameImageUrl();

    int getPlatinumCount();

    int getGoldCount();

    int getSilverCount();

    int getBronzeCount();

    int getEarnedPlatinumCount();

    int getEarnedGoldCount();

    int getEarnedSilverCount();

    int getEarnedBronzeCount();
}
