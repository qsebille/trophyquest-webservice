package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    @Query(value = """
            SELECT COUNT(*)
            FROM app.game g
                JOIN app.played_game pg ON pg.game_id = g.id
            WHERE pg.player_id = :playerId
            """, nativeQuery = true)
    long getTotalPlayerGamesPlayed(@Param("playerId") UUID playerId);

    @Query(value = """
            SELECT g.*
            FROM app.game g
                     INNER JOIN (SELECT pg.game_id,
                                        pg.player_id,
                                        pg.last_played_at AS last_played_at,
                                        COUNT(*) AS players_count
                                 FROM app.played_game pg
                                 WHERE pg.last_played_at >= now() - interval '7 days'
                                 GROUP BY pg.game_id, pg.player_id) player_count ON g.id = player_count.game_id
            ORDER BY players_count DESC, last_played_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Game> fetchRecentlyPlayedGames(@Param("limit") int limit, @Param("offset") int offset);
}
