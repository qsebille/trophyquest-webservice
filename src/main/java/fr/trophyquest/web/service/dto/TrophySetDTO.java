package fr.trophyquest.web.service.dto;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record TrophySetDTO(
        UUID id,
        String title,
        String platform,
        String imageUrl,
        Set<IgdbCandidateDTO> igdbGameCandidates
) {
}
