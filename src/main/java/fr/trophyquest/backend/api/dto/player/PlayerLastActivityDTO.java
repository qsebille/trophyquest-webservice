package fr.trophyquest.backend.api.dto.player;

import fr.trophyquest.backend.api.dto.trophy.TrophyDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetDTO;
import lombok.Builder;

@Builder
public record PlayerLastActivityDTO(
        TrophySetDTO lastPlayedTrophySet,
        TrophyDTO lastEarnedTrophy
) {
}
