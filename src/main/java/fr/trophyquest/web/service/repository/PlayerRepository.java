package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Player;
import fr.trophyquest.web.service.entity.projections.ActivePlayerProjection;
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
                   lg.last_game_platform,
                   lg.last_game_image_url,
                   lg.last_game_aws_image_url,
                   lg.last_earned_at,
                   trophy_count.platinum AS platinum_trophy_count,
                   trophy_count.gold     AS gold_trophy_count,
                   trophy_count.silver   AS silver_trophy_count,
                   trophy_count.bronze   AS bronze_trophy_count
            FROM app.player p
                     -- Count trophies by type
                     JOIN (SELECT et.player_id                                       AS player_id,
                                       COUNT(*) FILTER (WHERE t.trophy_type = 'platinum') AS platinum,
                                       COUNT(*) FILTER (WHERE t.trophy_type = 'gold')     AS gold,
                                       COUNT(*) FILTER (WHERE t.trophy_type = 'silver')   AS silver,
                                       COUNT(*) FILTER (WHERE t.trophy_type = 'bronze')   AS bronze
                                FROM app.earned_trophy et
                                         JOIN app.trophy t ON t.id = et.trophy_id
                                GROUP BY et.player_id) trophy_count ON trophy_count.player_id = p.id
            
                -- Total played games
                     JOIN (SELECT player_id,
                                       COUNT(DISTINCT game_id) AS total_games_played
                                FROM app.played_game
                                GROUP BY player_id) pg_stats ON pg_stats.player_id = p.id
            
                -- Last earned trophy game
                     JOIN (SELECT DISTINCT ON (et.player_id) et.player_id,
                                                                  g.id            AS last_game_id,
                                                                  g.title         AS last_game_title,
                                                                  g.platform      AS last_game_platform,
                                                                  g.image_url     AS last_game_image_url,
                                                                  g.aws_image_url AS last_game_aws_image_url,
                                                                  et.earned_at    AS last_earned_at
                                FROM app.earned_trophy et
                                         JOIN app.trophy t ON t.id = et.trophy_id
                                         JOIN app.game g ON g.id = t.game_id
                                ORDER BY et.player_id, et.earned_at DESC) lg ON lg.player_id = p.id
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
            SELECT p.id,
                   p.pseudo,
                   p.aws_avatar_url,
                   p.avatar_url,
                   trophy_stats.trophy_count,
                   trophy_stats.last_earned_at,
                   last_trophies_agg.trophies_json
            FROM app.player p
                     -- Recent trophies count and last earned time
                     JOIN (SELECT et.player_id,
                                  COUNT(*)          AS trophy_count,
                                  MAX(et.earned_at) AS last_earned_at
                           FROM app.earned_trophy et
                           WHERE et.earned_at > now() - interval '7 days'
                           GROUP BY et.player_id
                           ORDER BY trophy_count DESC) trophy_stats ON p.id = trophy_stats.player_id
            
            
                -- List of 5 last recent trophies
                     JOIN (SELECT lt.player_id,
                                  jsonb_agg(
                                          jsonb_build_object(
                                                  'id', lt.id,
                                                  'title', lt.title,
                                                  'description', lt.description,
                                                  'trophyType', lt.trophy_type,
                                                  'awsIconUrl', lt.aws_icon_url,
                                                  'iconUrl', lt.icon_url,
                                                  'obtainedAt', lt.earned_at,
                                                  'gameTitle', lt.game_title
                                          )
                                          ORDER BY lt.row_number
                                  )::text AS trophies_json
                           FROM (SELECT et.player_id,
                                        et.earned_at,
                                        t.id,
                                        t.title,
                                        t.description,
                                        t.trophy_type,
                                        t.aws_icon_url,
                                        t.icon_url,
                                        g.title           AS game_title,
                                        row_number()
                                        OVER (PARTITION BY et.player_id ORDER BY et.earned_at DESC) AS row_number
                                 FROM app.earned_trophy et
                                          JOIN app.trophy t ON t.id = et.trophy_id
                                          JOIN app.game g ON g.id = t.game_id
                                 WHERE et.earned_at > now() - interval '7 days'
                                 ORDER BY row_number DESC) as lt
                           WHERE lt.row_number <= 5
                           GROUP BY lt.player_id) last_trophies_agg ON last_trophies_agg.player_id = p.id
            
            WHERE trophy_count > 0
            ORDER BY trophy_count DESC, last_earned_at DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<ActivePlayerProjection> fetchMostActivePlayerTrophies(@Param("limit") int limit);

    List<Player> findByPseudo(String pseudo);
}
