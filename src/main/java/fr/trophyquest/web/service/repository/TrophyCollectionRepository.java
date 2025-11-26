package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.TrophyCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TrophyCollectionRepository extends JpaRepository<TrophyCollection, UUID> {

    @Query(value = """
            SELECT tc.*
            FROM app.trophy_collection tc
                JOIN app.game g ON tc.game_id = g.id
                JOIN app.user_trophy_collection utc ON utc.trophy_collection_id = tc.id
            WHERE utc.user_id = :userId AND g.id IN :gameIds
            """, nativeQuery = true)
    List<TrophyCollection> findByUserAndGames(@Param("userId") UUID userId, @Param("gameIds") Set<UUID> gameIds);
}
