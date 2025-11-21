package fr.trophyquest.web.service.entity.projections;

import java.util.UUID;

public interface UserGameProjection {
    UUID getId();

    String getTitle();

    String getImageUrl();

    String getPlatform();

    int getEarnedPlatinum();

    int getEarnedGold();

    int getEarnedSilver();

    int getEarnedBronze();

    int getTotalPlatinum();

    int getTotalGold();

    int getTotalSilver();

    int getTotalBronze();
}
