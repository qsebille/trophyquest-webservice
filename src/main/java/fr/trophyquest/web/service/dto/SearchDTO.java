package fr.trophyquest.web.service.dto;

import java.util.List;

public record SearchDTO<T>(
        List<T> content,
        long total
) {
}
