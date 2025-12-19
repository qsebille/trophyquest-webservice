package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Player;
import fr.trophyquest.web.service.entity.projections.ActivePlayerTrophyProjection;
import fr.trophyquest.web.service.entity.projections.PlayerSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query(value = """
            SELECT p.id,
                   p.pseudo,
                   p.aws_avatar_url,
                   p.avatar_url,
                   pg_stats.total_games_played,
                   lg.last_game_id,
                   lg.last_game_title,
                   lg.last_game_image_url,
                   lg.last_game_aws_image_url,
                   lg.last_earned_at,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'platinum') AS platinum_trophy_count,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'gold')     AS gold_trophy_count,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'silver')   AS silver_trophy_count,
                   COUNT(*) FILTER (WHERE t.trophy_type = 'bronze')   AS bronze_trophy_count
            FROM app.player p
                     JOIN app.earned_trophy et ON et.player_id = p.id
                     JOIN app.trophy t ON t.id = et.trophy_id
            
                -- Total played games
                     LEFT JOIN (SELECT player_id,
                                       COUNT(DISTINCT game_id) AS total_games_played
                                FROM app.played_game
                                GROUP BY player_id) pg_stats ON pg_stats.player_id = p.id
            
                -- Last earned trophy game
                     LEFT JOIN (SELECT DISTINCT ON (et.player_id) et.player_id,
                                                                  g.id             AS last_game_id,
                                                                  g.title          AS last_game_title,
                                                                  g.image_url      AS last_game_image_url,
                                                                  g.aws_image_url  AS last_game_aws_image_url,
                                                                  et.earned_at     AS last_earned_at
                                FROM app.earned_trophy et
                                         JOIN app.trophy t ON t.id = et.trophy_id
                                         JOIN app.game g ON g.id = t.game_id
                                ORDER BY et.player_id, et.earned_at DESC) lg ON lg.player_id = p.id
            
            GROUP BY p.id,
                     p.pseudo,
                     p.aws_avatar_url,
                     p.avatar_url,
                     pg_stats.total_games_played,
                     lg.last_game_id,
                     lg.last_game_title,
                     lg.last_game_image_url,
                     lg.last_game_aws_image_url,
                     lg.last_earned_at
            ORDER BY last_earned_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<PlayerSummaryProjection> search(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = """
            SELECT COUNT(DISTINCT (p.id))
            FROM app.player p
                JOIN app.earned_trophy et ON et.player_id = p.id
            """, nativeQuery = true)
    long count();

    @Query(value = """
            SELECT p.id                             AS player_id,
                   p.pseudo                         AS player_pseudo,
                   p.aws_avatar_url                 AS player_aws_avatar_url,
                   p.avatar_url                     AS player_avatar_url,
                   recent_trophy_count.trophy_count AS trophy_count,
                   t.id                             AS trophy_id,
                   t.title                          AS trophy_title,
                   t.description                    AS trophy_description,
                   t.trophy_type                    AS trophy_type,
                   t.aws_icon_url                   AS trophy_aws_icon_url,
                   t.icon_url                       AS trophy_icon_url,
                   g.title                          AS game_title,
                   recent_trophy_earned.earned_at   AS obtained_at
            FROM app.player p
                     JOIN (SELECT et1.player_id,
                                  COUNT(*) AS trophy_count
                           FROM app.earned_trophy et1
                           WHERE et1.earned_at > now() - interval '7 days'
                           GROUP BY et1.player_id
                           ORDER BY trophy_count DESC) recent_trophy_count ON p.id = recent_trophy_count.player_id
                     JOIN (SELECT et2.player_id,
                                  et2.trophy_id,
                                  et2.earned_at,
                                  row_number() over (partition by et2.player_id order by et2.earned_at desc) AS row_number
                           FROM app.earned_trophy et2
                           WHERE et2.earned_at > now() - interval '7 days') recent_trophy_earned
                          ON recent_trophy_earned.player_id = p.id AND recent_trophy_earned.row_number <= 5
                     JOIN app.trophy t ON t.id = recent_trophy_earned.trophy_id
                     JOIN app.game g ON g.id = t.game_id
            ORDER BY trophy_count DESC, obtained_at DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<ActivePlayerTrophyProjection> fetchMostActivePlayerTrophies(@Param("limit") int limit);
}
