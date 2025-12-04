package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Trophy;
import fr.trophyquest.web.service.entity.projections.ObtainedTrophyProjection;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
import fr.trophyquest.web.service.entity.projections.TrophyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrophyRepository extends JpaRepository<Trophy, UUID> {

    @Query(value = """
            SELECT t.trophy_type AS trophy_type,
                   COUNT(*)      AS trophy_count
            FROM app.trophy t
                JOIN app.user_trophy ut ON ut.trophy_id = t.id
            WHERE ut.user_id = :userId
            GROUP BY t.trophy_type
            """, nativeQuery = true)
    List<TrophyCountProjection> fetchTrophyCount(@Param("userId") UUID userId);

    @Query(value = """
            SELECT t.id,
                   t.rank,
                   t.title AS trophy_title,
                   t.description AS trophy_description,
                   t.trophy_type AS trophy_type,
                   t.is_hidden,
                   t.icon_url,
                   g.title as game_title,
                   t.game_group_id as game_group,
                   ut.earned_at
            FROM app.user_trophy ut
            JOIN app.trophy t ON ut.trophy_id = t.id
            JOIN app.trophy_collection tc ON t.trophy_collection_id = tc.id
            JOIN app.game g ON tc.game_id = g.id
            WHERE user_id = :userId
            ORDER BY earned_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<TrophyProjection> searchUserEarnedTrophies(
            @Param("userId") UUID userId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM app.user_trophy
            WHERE user_id = :userId
            """, nativeQuery = true)
    long getTotalEarnedTrophiesForUser(@Param("userId") UUID userId);


    @Query(value = """
            SELECT t.id,
                   t.rank,
                   t.title AS trophy_title,
                   t.description AS trophy_description,
                   t.trophy_type AS trophy_type,
                   t.is_hidden,
                   t.icon_url,
                   g.title as game_title,
                   t.game_group_id as game_group,
                   ut.earned_at
            FROM app.trophy t
                     JOIN app.trophy_collection tc ON t.trophy_collection_id = tc.id
                     JOIN app.game g ON tc.game_id = g.id
                     LEFT JOIN app.user_trophy ut ON ut.trophy_id = t.id AND ut.user_id = :userId
            WHERE tc.id = :collectionId
            ORDER BY rank
            """, nativeQuery = true)
    List<TrophyProjection> fetchUserCollectionTrophies(
            @Param("userId") UUID userId,
            @Param("collectionId") UUID collectionId
    );

    @Query(value = """
            SELECT t.id,
                   t.title       as trophy_title,
                   t.trophy_type,
                   t.description as trophy_description,
                   t.icon_url    as trophy_icon_url,
                   g.title       as game_title,
                   ut.earned_at  as obtained_at,
                   up.name       as obtained_by
            FROM app.trophy t
                     JOIN app.user_trophy ut ON ut.trophy_id = t.id
                     JOIN app.trophy_collection tc ON tc.id = t.trophy_collection_id
                     JOIN app.game g ON g.id = tc.game_id
                     JOIN app.user_profile up ON up.id = ut.user_id
            ORDER BY obtained_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<ObtainedTrophyProjection> searchObtainedTrophies(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = """
            SELECT COUNT(*)
            FROM app.user_trophy
            """, nativeQuery = true)
    long getTotalEarnedTrophies();
}
