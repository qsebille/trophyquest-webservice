package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.IgdbCandidate;
import fr.trophyquest.web.service.entity.projections.GameCandidatesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IgdbCandidateRepository extends JpaRepository<IgdbCandidate, Long> {

    @Query(value = """
            SELECT game.id            AS game_id,
                   game.title         AS game_title,
                   game.aws_image_url AS game_aws_image_url,
                   game.image_url     AS game_image_url,
                   jsonb_agg(
                           jsonb_build_object(
                                   'id', igdb_game.id,
                                   'name', igdb_game.name,
                                   'cover', igdb_game.cover,
                                   'releaseDate', igdb_game.release_date
                           )
                   )::text AS candidates_json
            FROM app.igdb_candidate
                     JOIN app.igdb_game ON igdb_game.id = igdb_candidate.candidate_id
                     JOIN app.game ON igdb_candidate.game_id = game.id
            WHERE status = 'PENDING'
            GROUP BY game.id
            LIMIT :limit OFFSET :offset;
            """, nativeQuery = true)
    List<GameCandidatesProjection> searchForGameCandidates(@Param("limit") long limit, @Param("offset") long offset);
}
