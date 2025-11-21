package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.projections.UserGameProjection;
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
                tc.platform,
                MAX(ut.earned_at) AS last_earned_at,
                COUNT(*) FILTER (WHERE t.trophy_type = 'platinum')                            AS total_platinum,
                COUNT(*) FILTER (WHERE t.trophy_type = 'gold')                                AS total_gold,
                COUNT(*) FILTER (WHERE t.trophy_type = 'silver')                              AS total_silver,
                COUNT(*) FILTER (WHERE t.trophy_type = 'bronze')                              AS total_bronze,
                COUNT(*) FILTER (WHERE t.trophy_type = 'platinum' AND ut.user_id IS NOT NULL) AS earned_platinum,
                COUNT(*) FILTER (WHERE t.trophy_type = 'gold'     AND ut.user_id IS NOT NULL) AS earned_gold,
                COUNT(*) FILTER (WHERE t.trophy_type = 'silver'   AND ut.user_id IS NOT NULL) AS earned_silver,
                COUNT(*) FILTER (WHERE t.trophy_type = 'bronze'   AND ut.user_id IS NOT NULL) AS earned_bronze
            FROM app.game g
                     JOIN app.user_game ug
                          ON ug.game_id = g.id
                     JOIN app.user_profile up
                          ON up.id = ug.user_id
                     JOIN app.trophy_collection tc
                          ON tc.game_id = g.id
                     JOIN app.trophy t
                          ON t.trophy_collection_id = tc.id
                     LEFT JOIN app.user_trophy ut
                               ON ut.trophy_id = t.id
                                   AND ut.user_id = up.id
            WHERE up.id = :userId
            GROUP BY
                g.id,
                g.title,
                g.image_url,
                tc.platform
            ORDER BY
                last_earned_at DESC NULLS LAST
            LIMIT :pageSize OFFSET :pageNumber * :pageSize
            """, nativeQuery = true)
    List<UserGameProjection> searchByUserId(
            @Param("userId") UUID userId,
            @Param("pageNumber") int pageNumber,
            @Param("pageSize") int pageSize
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM app.game g
                     JOIN app.user_game ug ON ug.game_id = g.id
                     JOIN app.user_profile up ON up.id = ug.user_id
            WHERE up.id = :userId
            """, nativeQuery = true)
    long countDistinctByUserId(@Param("userId") UUID userId);

}
