package fr.trophyquest.web.service.entity.projections;

public record CandidateProjection(
        Long id,
        String name,
        String cover,
        String releaseDate,
        Long score
) {
}
