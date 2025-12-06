package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Player;
import fr.trophyquest.web.service.entity.projections.PlayerWithTrophyCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query(value = """
            SELECT p.id,
                   p.pseudo,
                   p.avatar_url,
                   pg_stats.total_games_played,
                   lg.last_collection_id,
                   lg.last_game_id,
                   lg.last_game_title,
                   lg.last_game_image_url,
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
                                                                  g.id         AS last_game_id,
                                                                  g.image_url  AS last_game_image_url,
                                                                  g.title      AS last_game_title,
                                                                  tc.id        AS last_collection_id,
                                                                  et.earned_at AS last_earned_at
                                FROM app.earned_trophy et
                                         JOIN app.trophy t ON t.id = et.trophy_id
                                         JOIN app.trophy_collection tc ON tc.id = t.trophy_collection_id
                                         JOIN app.game g ON g.id = tc.game_id
                                ORDER BY et.player_id, et.earned_at DESC) lg ON lg.player_id = p.id
            
            GROUP BY p.id,
                     p.pseudo,
                     p.avatar_url,
                     pg_stats.total_games_played,
                     lg.last_collection_id,
                     lg.last_game_id,
                     lg.last_game_title,
                     lg.last_game_image_url,
                     lg.last_earned_at
            ORDER BY last_earned_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<PlayerWithTrophyCountProjection> search(@Param("limit") int limit, @Param("offset") int offset);

}
