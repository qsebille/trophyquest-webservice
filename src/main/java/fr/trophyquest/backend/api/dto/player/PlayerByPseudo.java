package fr.trophyquest.backend.api.dto.player;

import lombok.Builder;

@Builder
public record PlayerByPseudo(
        String status,
        PlayerDTO player
) {
}
