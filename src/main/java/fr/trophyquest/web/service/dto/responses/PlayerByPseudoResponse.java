package fr.trophyquest.web.service.dto.responses;

import fr.trophyquest.web.service.dto.PlayerDTO;
import lombok.Builder;

@Builder
public record PlayerByPseudoResponse(
        String status,
        PlayerDTO player
) {
}
