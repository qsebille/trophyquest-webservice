package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TrophySetDTO(
        UUID id,
        String title,
        String platform,
        String imageUrl,
        List<IgdbCandidateDTO> igdbGameCandidates
) {
}
