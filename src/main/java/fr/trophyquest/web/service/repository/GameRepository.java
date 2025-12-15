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
            SELECT g.*,
                   MAX(pg.last_played_at) AS last_played_at,
                   COUNT(*) AS players_count
            FROM app.played_game pg
                     JOIN app.game g ON pg.game_id = g.id
            WHERE pg.last_played_at >= now() - interval '7 days'
            GROUP BY g.id, g.title, g.image_url, g.created_at, g.updated_at, g.aws_image_url
            ORDER BY players_count DESC, last_played_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Game> fetchRecentlyPlayedGames(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = """
            SELECT COUNT( DISTINCT game_id )
            FROM app.played_game pg
            WHERE pg.last_played_at >= now() - interval '7 days'
            """, nativeQuery = true)
    long countRecentlyPlayedGames();

}
