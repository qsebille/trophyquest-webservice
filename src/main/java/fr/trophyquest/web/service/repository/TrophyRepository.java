package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Trophy;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
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

}
