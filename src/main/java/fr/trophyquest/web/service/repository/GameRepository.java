package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.projections.PlayedGameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    @Query(value = """
            SELECT
                g.id,
                g.title,
                g.image_url,
                MAX(ut.earned_at) AS last_earned_at,
                COUNT(*) FILTER (WHERE t.trophy_type = 'platinum')                            AS total_platinum,
                COUNT(*) FILTER (WHERE t.trophy_type = 'gold')                                AS total_gold,
                COUNT(*) FILTER (WHERE t.trophy_type = 'silver')                              AS total_silver,
                COUNT(*) FILTER (WHERE t.trophy_type = 'bronze')                              AS total_bronze,
                COUNT(*) FILTER (WHERE t.trophy_type = 'platinum' AND et.player_id IS NOT NULL) AS earned_platinum,
                COUNT(*) FILTER (WHERE t.trophy_type = 'gold'     AND et.player_id IS NOT NULL) AS earned_gold,
                COUNT(*) FILTER (WHERE t.trophy_type = 'silver'   AND et.player_id IS NOT NULL) AS earned_silver,
                COUNT(*) FILTER (WHERE t.trophy_type = 'bronze'   AND et.player_id IS NOT NULL) AS earned_bronze
            FROM app.game g
                     JOIN app.played_game pg ON pg.game_id = g.id
                     JOIN app.player p ON p.id = pg.player_id
                     JOIN app.trophy_collection tc ON tc.game_id = g.id
                     JOIN app.trophy t ON t.trophy_collection_id = tc.id
                     LEFT JOIN app.earned_trophy et ON et.trophy_id = t.id AND et.player_id = p.id
            WHERE p.id = :playerId
            GROUP BY
                g.id,
                g.title,
                g.image_url
            ORDER BY
                last_earned_at DESC NULLS LAST
            LIMIT :pageSize OFFSET :pageNumber * :pageSize
            """, nativeQuery = true)
    List<PlayedGameProjection> searchGamesPlayedByPlayer(
            @Param("playerId") UUID playerId,
            @Param("pageNumber") int pageNumber,
            @Param("pageSize") int pageSize
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM app.game g
                     JOIN app.played_game pg ON pg.game_id = g.id
                     JOIN app.player p ON p.id = pg.player_id
            WHERE p.id = :playerId
            """, nativeQuery = true)
    long countPlayedGamesByPlayer(@Param("playerId") UUID playerId);

}
