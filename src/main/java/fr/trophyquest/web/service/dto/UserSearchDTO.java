package fr.trophyquest.web.service.dto;

import java.util.List;

public record UserSearchDTO(
        List<UserProfileDTO> content,
        long totalElements
) {
}
