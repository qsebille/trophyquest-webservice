package fr.trophyquest.web.service.entity.projections;

import java.util.UUID;

public interface PlayedGameProjection {
    UUID getId();

    String getTitle();

    String getImageUrl();

    int getEarnedPlatinum();

    int getEarnedGold();

    int getEarnedSilver();

    int getEarnedBronze();

    int getTotalPlatinum();

    int getTotalGold();

    int getTotalSilver();

    int getTotalBronze();
}
