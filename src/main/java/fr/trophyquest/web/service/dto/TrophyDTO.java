package fr.trophyquest.web.service.dto;

import java.util.UUID;

public record TrophyDTO(
        UUID id,
        String name,
        String detail,
        String trophyType,
        Boolean isHidden,
        String iconUrl,
        String gameName,
        String gameGroup,
        String quickGuide,
        String youtubeVideoUrl,
        String youtubeThumbnailUrl
) {
}
