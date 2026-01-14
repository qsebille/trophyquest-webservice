package fr.trophyquest.backend.domain.projection;

import java.util.UUID;

public interface RecentTrophySetRow {
    UUID getId();

    String getTitle();

    String getImage();

    Long getRecentPlayers();
}
