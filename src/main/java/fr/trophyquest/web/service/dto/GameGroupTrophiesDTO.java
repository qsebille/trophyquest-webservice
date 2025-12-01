package fr.trophyquest.web.service.dto;

import java.util.List;

public record GameGroupTrophiesDTO(
        String groupName,
        List<TrophyDTO> trophies
) {
}
