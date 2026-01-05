package fr.trophyquest.web.service.entity.projections;

import java.util.Optional;
import java.util.UUID;

public interface GameCandidatesProjection {
    UUID getGameId();

    String getGameTitle();

    Optional<String> getGameAwsImageUrl();

    String getGameImageUrl();

    String getCandidatesJson();
}
