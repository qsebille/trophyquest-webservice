package fr.trophyquest.web.service.entity.projections;

import java.time.Instant;
import java.util.UUID;

public interface EarnedTrophyProjection {
    UUID getTrophyId();

    String getTrophyTitle();

    String getTrophyDetail();

    String getTrophyType();

    Instant getEarnedDate();
}