package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;
import java.util.UUID;

public interface PlayerSummaryProjection {
    UUID getId();

    String getPseudo();

    Optional<String> getAwsAvatarUrl();

    String getAvatarUrl();

    int getPlatinumTrophyCount();

    int getGoldTrophyCount();

    int getSilverTrophyCount();

    int getBronzeTrophyCount();

    int getTotalGamesPlayed();

    UUID getLastCollectionId();

    UUID getLastGameId();

    String getLastGameTitle();

    Optional<String> getLastGameAwsImageUrl();

    String getLastGameImageUrl();
}
