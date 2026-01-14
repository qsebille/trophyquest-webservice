package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.api.dto.trophyset.PlayedTrophySetSearchItemDTO;
import fr.trophyquest.backend.domain.entity.PlayedTrophySet;
import fr.trophyquest.backend.domain.entity.TrophySet;
import fr.trophyquest.backend.domain.entity.embedded.PlayedTrophySetId;
import fr.trophyquest.backend.domain.projection.RecentTrophySetRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlayedTrophySetRepository extends JpaRepository<PlayedTrophySet, PlayedTrophySetId> {

    @Query("""
            select pts.trophySet
            from PlayedTrophySet pts
            where pts.player.id = :playerId
            order by pts.lastPlayedAt desc
            """)
    List<TrophySet> listByPlayerId(UUID playerId);

    @Query(
            value = """
                    with trophy_count as (
                        select ts.id,
                               count(*) as total_trophies,
                               count(*) filter ( where t.trophy_type = 'platinum' and et.earned_at is not null ) as total_earned_platinum,
                               count(*) filter ( where t.trophy_type = 'gold' and et.earned_at is not null ) as total_earned_gold,
                               count(*) filter ( where t.trophy_type = 'silver' and et.earned_at is not null ) as total_earned_silver,
                               count(*) filter ( where t.trophy_type = 'bronze' and et.earned_at is not null ) as total_earned_bronze
                        from app.trophy_set ts
                        join app.trophy t on ts.id = t.trophy_set_id
                        left join app.earned_trophy et on t.id = et.trophy_id
                        group by ts.id
                    )
                    
                    select ts.id,
                           ts.title,
                           ts.platform,
                           coalesce(ts.aws_image_url, ts.image_url) as image,
                           pts.last_played_at,
                           trophy_count.total_trophies,
                           trophy_count.total_earned_platinum,
                           trophy_count.total_earned_gold,
                           trophy_count.total_earned_silver,
                           trophy_count.total_earned_bronze
                    from app.played_trophy_set pts
                             join app.trophy_set ts on ts.id = pts.trophy_set_id
                            join trophy_count on trophy_count.id = ts.id
                    where pts.player_id = :playerId
                    order by pts.last_played_at desc
                    limit :limit offset :offset
                    """,
            nativeQuery = true
    )
    List<PlayedTrophySetSearchItemDTO> searchPlayedTrophySetsByPlayer(
            @Param("playerId") UUID playerId,
            int limit,
            int offset
    );

    @Query("select count(pts) from PlayedTrophySet pts where pts.player.id = :playerId")
    long countPlayedTrophySetsByPlayer(UUID playerId);

    @Query("""
            select count( distinct pts.player.id )
            from PlayedTrophySet pts
            where pts.lastPlayedAt >= :limitDate
            """)
    long countRecentPlayers(@Param("limitDate") Instant limitDate);

    @Query("""
            select count(distinct pts.trophySet.id )
            from PlayedTrophySet pts
            where pts.lastPlayedAt >= :limitDate
            """)
    long countRecentTrophySets(@Param("limitDate") Instant limitDate);

    @Query(value = """
            select ts.id,
                   ts.title,
                   coalesce(ts.aws_image_url, ts.image_url) as image,
                   count(distinct pts.player_id)            as recent_players,
                   max(pts.last_played_at)                  as last_played_at
            from app.played_trophy_set pts
                join app.trophy_set ts on ts.id = pts.trophy_set_id
            where pts.last_played_at >= :limitDate
            group by ts.id
            order by recent_players, last_played_at
            """, nativeQuery = true)
    List<RecentTrophySetRow> fetchRecentTrophySets(@Param("limitDate") Instant limitDate);
}
