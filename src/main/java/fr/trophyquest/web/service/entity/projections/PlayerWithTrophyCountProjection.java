package fr.trophyquest.web.service.entity.projections;

import java.util.UUID;

public interface PlayerWithTrophyCountProjection {
    UUID getId();

    String getPseudo();

    String getAvatarUrl();

    int getPlatinumTrophyCount();

    int getGoldTrophyCount();

    int getSilverTrophyCount();

    int getBronzeTrophyCount();

    int getTotalGamesPlayed();

    UUID getLastCollectionId();

    UUID getLastGameId();

    String getLastGameTitle();

    String getLastGameImageUrl();
}
