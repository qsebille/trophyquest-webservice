package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.projections.GameSummaryProjection;
import fr.trophyquest.web.service.entity.projections.PlayerGameProjection;
import fr.trophyquest.web.service.entity.projections.PopularGameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    @Query(value = """
            SELECT COUNT(*)
            FROM app.played_game pg
            WHERE pg.player_id = :playerId
            """, nativeQuery = true)
    long getTotalPlayedGamesForPlayer(@Param("playerId") UUID playerId);

    @Query(value = """
            SELECT g.id,
                   g.title,
                   g.platform,
                   g.aws_image_url,
                   g.image_url,
                   max_earned_at,
                   platinum_count,
                   gold_count,
                   silver_count,
                   bronze_count,
                   earned_platinum_count,
                   earned_gold_count,
                   earned_silver_count,
                   earned_bronze_count
            FROM app.played_game pg
                     JOIN app.game g ON g.id = pg.game_id
                     JOIN (SELECT g.id                                                                   AS game_id,
                                  MAX(earned_at)                                                         AS max_earned_at,
                                  COUNT(*) FILTER (WHERE t.trophy_type = 'platinum')                     AS platinum_count,
                                  COUNT(*) FILTER (WHERE t.trophy_type = 'gold')                         AS gold_count,
                                  COUNT(*) FILTER (WHERE t.trophy_type = 'silver')                       AS silver_count,
                                  COUNT(*) FILTER (WHERE t.trophy_type = 'bronze')                       AS bronze_count,
                                  COUNT(*)
                                  FILTER (WHERE t.trophy_type = 'platinum' AND et.earned_at IS NOT NULL) AS earned_platinum_count,
                                  COUNT(*)
                                  FILTER (WHERE t.trophy_type = 'gold' AND et.earned_at IS NOT NULL)     AS earned_gold_count,
                                  COUNT(*)
                                  FILTER (WHERE t.trophy_type = 'silver' AND et.earned_at IS NOT NULL)   AS earned_silver_count,
                                  COUNT(*)
                                  FILTER (WHERE t.trophy_type = 'bronze' AND et.earned_at IS NOT NULL)   AS earned_bronze_count
                           FROM app.trophy t
                                    JOIN app.game g ON g.id = t.game_id
                                    LEFT JOIN app.earned_trophy et ON et.trophy_id = t.id AND et.player_id = :playerId
                           GROUP BY g.id) game_trophies ON game_trophies.game_id = g.id
            WHERE pg.player_id = :playerId AND max_earned_at IS NOT NULL
            ORDER BY max_earned_at DESC
            LIMIT :limit OFFSET :offset;
            """, nativeQuery = true)
    List<PlayerGameProjection> searchGamesForPlayer(
            @Param("playerId") UUID playerId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
            SELECT g.id,
                   g.title,
                   g.image_url,
                   g.aws_image_url,
                   MAX(pg.last_played_at) AS last_played_at,
                   COUNT(*) AS players_count
            FROM app.played_game pg
                     JOIN app.game g ON pg.game_id = g.id
            WHERE pg.last_played_at >= now() - interval '7 days'
            GROUP BY g.id, g.title, g.image_url, g.aws_image_url
            ORDER BY players_count DESC, last_played_at DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<PopularGameProjection> fetchRecentlyPlayedGames(@Param("limit") int limit);

    @Query(value = """
            SELECT g.id,
                   g.title,
                   g.platform,
                   g.image_url,
                   g.aws_image_url,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'platinum') AS platinum_count,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'gold') AS gold_count,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'silver') AS silver_count,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'bronze') AS bronze_count
            FROM app.game g
                     JOIN app.trophy t ON t.game_id = g.id
            WHERE g.id = :gameId
            GROUP BY g.id, g.title, g.image_url, g.aws_image_url
            """, nativeQuery = true)
    GameSummaryProjection getSummaryById(UUID gameId);

}
