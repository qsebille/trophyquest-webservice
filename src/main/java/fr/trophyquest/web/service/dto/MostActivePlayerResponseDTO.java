package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MostActivePlayerResponseDTO(
        PlayerDTO player,
        int recentTrophyCount,
        List<ObtainedTrophyDTO> lastObtainedTrophies
) {
}
