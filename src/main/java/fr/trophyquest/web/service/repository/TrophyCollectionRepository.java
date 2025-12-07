package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.TrophyCollection;
import fr.trophyquest.web.service.entity.projections.PlayerCollectionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrophyCollectionRepository extends JpaRepository<TrophyCollection, UUID> {

    @Query(value = """
            SELECT tc.id             AS collection_id,
                   tc.title          AS collection_title,
                   tc.platform       AS collection_platform,
                   tc.image_url      AS collection_image_url,
                   g.id              AS game_id,
                   g.title           AS game_title,
                   max_earned_at,
                   platinum_count,
                   gold_count,
                   silver_count,
                   bronze_count,
                   earned_platinum_count,
                   earned_gold_count,
                   earned_silver_count,
                   earned_bronze_count
            FROM app.trophy_collection tc
                     JOIN app.played_trophy_collection ptc ON tc.id = ptc.trophy_collection_id AND ptc.player_id = :playerId
                     JOIN app.game g ON g.id = tc.game_id
                     JOIN (SELECT tc.id                                                                  AS collection_id,
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
                                    JOIN app.trophy_collection tc ON tc.id = t.trophy_collection_id
                                    LEFT JOIN app.earned_trophy et ON et.trophy_id = t.id AND et.player_id = :playerId
                           GROUP BY tc.id) collection_trophies ON collection_trophies.collection_id = tc.id
            WHERE ptc.player_id = :playerId AND max_earned_at IS NOT NULL
            ORDER BY max_earned_at DESC
            LIMIT :limit OFFSET :offset;
            """, nativeQuery = true)
    List<PlayerCollectionProjection> searchPlayerTrophyCollections(
            @Param("playerId") UUID playerId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM app.trophy_collection tc
                JOIN app.played_trophy_collection ptc ON ptc.trophy_collection_id = tc.id
            WHERE ptc.player_id = :playerId
            """, nativeQuery = true)
    long getTotalPlayerTrophyCollections(@Param("playerId") UUID playerId);

}
