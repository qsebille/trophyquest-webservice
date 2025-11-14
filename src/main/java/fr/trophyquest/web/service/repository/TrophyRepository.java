package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Trophy;
import fr.trophyquest.web.service.entity.projections.EarnedTrophyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrophyRepository extends JpaRepository<Trophy, UUID> {

    List<Trophy> findByGameId(UUID gameId);

    @Query(value = """
            SELECT t.id AS trophyId,
                   t.title AS trophyTitle,
                   t.detail AS trophyDetail,
                   t.trophy_type AS trophyType,
                   ut.earned_at AS earnedDate
            FROM trophy t
            JOIN user_trophy ut ON ut.trophy_id = t.id
            WHERE ut.user_id = :userId
            ORDER BY earnedDate DESC
            """,
            nativeQuery = true)
    List<EarnedTrophyProjection> findEarnedTrophiesByUserId(@Param("userId") UUID userId);

}
