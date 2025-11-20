package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    @Query(value = """
            SELECT g.*
            FROM app.game g
                     JOIN app.user_game ug ON ug.game_id = g.id
                     JOIN app.user_profile up ON up.id = ug.user_id
            WHERE up.id = :userId
            ORDER BY last_played_at DESC
            LIMIT :pageSize OFFSET :pageNumber * :pageSize
            """, nativeQuery = true)
    List<Game> searchByUserId(
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
