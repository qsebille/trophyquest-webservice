package fr.trophyquest.web.service.entity.projections;

import java.util.UUID;

public interface GameProjection {
    UUID getTitleId();

    String getTitleName();

    String getTrophySetId();

    String getTrophySetName();

    String getCategory();

    String getPlatform();

    String getImageUrl();
}