package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.api.dto.trophysuite.PlayedTrophySuiteSearchItemDTO;
import fr.trophyquest.backend.api.dto.trophysuite.RecentTrophySuiteDTO;
import fr.trophyquest.backend.domain.entity.PlayedTrophySuite;
import fr.trophyquest.backend.domain.entity.Player;
import fr.trophyquest.backend.domain.entity.TrophySuite;
import fr.trophyquest.backend.domain.entity.embedded.PlayedTrophySuiteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlayedTrophySuiteRepository extends JpaRepository<PlayedTrophySuite, PlayedTrophySuiteId> {

    @Query("""
            select pts.trophySuite
            from PlayedTrophySuite pts
            where pts.player = :player
            order by pts.lastPlayedAt desc
            """)
    List<TrophySuite> listByPlayerId(Player player);

    @Query(value = """
            with trophy_count as (
                select ts.id,
                       count(*) as total_trophies,
                       count(*) filter ( where t.trophy_type = 'platinum' and et.earned_at is not null ) as total_earned_platinum,
                       count(*) filter ( where t.trophy_type = 'gold' and et.earned_at is not null ) as total_earned_gold,
                       count(*) filter ( where t.trophy_type = 'silver' and et.earned_at is not null ) as total_earned_silver,
                       count(*) filter ( where t.trophy_type = 'bronze' and et.earned_at is not null ) as total_earned_bronze
                from app.psn_trophy_suite ts
                join app.psn_trophy t on ts.id = t.trophy_suite_id
                left join app.psn_earned_trophy et on t.id = et.trophy_id
                group by ts.id
            )
            
            select ts.id,
                   ts.name,
                   ts.platforms,
                   coalesce(ts.aws_image_url, ts.psn_image_url) as image,
                   pts.last_played_at,
                   trophy_count.total_trophies,
                   trophy_count.total_earned_platinum,
                   trophy_count.total_earned_gold,
                   trophy_count.total_earned_silver,
                   trophy_count.total_earned_bronze
            from app.psn_played_trophy_suite pts
                    join app.psn_trophy_suite ts on ts.id = pts.trophy_suite_id
                    join trophy_count on trophy_count.id = ts.id
            where pts.player_id = :playerId
            order by pts.last_played_at desc
            limit :limit offset :offset
            """,
            nativeQuery = true
    )
    List<PlayedTrophySuiteSearchItemDTO> searchPlayedTrophySuitesByPlayer(
            @Param("playerId") UUID playerId,
            int limit,
            int offset
    );

    @Query("select count(pts) from PlayedTrophySuite pts where pts.player.id = :playerId")
    long countPlayedTrophySuitesByPlayer(UUID playerId);

    @Query("""
            select count( distinct pts.player.id )
            from PlayedTrophySuite pts
            where pts.lastPlayedAt >= :limitDate
            """)
    long countRecentPlayers(@Param("limitDate") Instant limitDate);

    @Query("""
            select count(distinct pts.trophySuite.id )
            from PlayedTrophySuite pts
            where pts.lastPlayedAt >= :limitDate
            """)
    long countRecentPlayedTrophySuites(@Param("limitDate") Instant limitDate);

    @Query("""
            select new fr.trophyquest.backend.api.dto.trophysuite.RecentTrophySuiteDTO(
                ts.id,
                ts.name,
                ts.image,
                count(distinct(pts.player.id))
            )
            from PlayedTrophySuite pts
                join pts.trophySuite ts
            where pts.lastPlayedAt >= :limitDate
            """)
    List<RecentTrophySuiteDTO> fetchRecentPlayedTrophySuites(@Param("limitDate") Instant limitDate);
}
