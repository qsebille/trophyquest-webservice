package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    @Query(value = """
            SELECT COUNT(*)
            FROM app.game g
                JOIN app.played_game pg ON pg.game_id = g.id
            WHERE pg.player_id = :playerId
            """, nativeQuery = true)
    long getTotalPlayerGamesPlayed(@Param("playerId") UUID playerId);
}
