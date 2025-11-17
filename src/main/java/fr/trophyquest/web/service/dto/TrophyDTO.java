package fr.trophyquest.web.service.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record TrophyDTO(
        UUID id,
        String name,
        String detail,
        String trophyType,
        Boolean isHidden,
        String iconUrl,
        String trophySetName,
        String gameGroup,
        ZonedDateTime earnedTrophyDate,
        String quickGuide,
        String youtubeVideoUrl,
        String youtubeThumbnailUrl
) {
}
