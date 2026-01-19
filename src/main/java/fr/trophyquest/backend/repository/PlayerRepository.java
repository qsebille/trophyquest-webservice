package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.api.dto.player.PlayerSearchItemDTO;
import fr.trophyquest.backend.domain.entity.Player;
import fr.trophyquest.backend.domain.projection.RecentPlayerRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    @Query(value = """
            with player_search as (select p.id,
                                          p.pseudo,
                                          coalesce(p.aws_avatar_url, p.psn_avatar_url) as avatar,
                                          max(et.earned_at)                            as last_earned_at
                                   from app.psn_player p
                                            join app.psn_earned_trophy et on p.id = et.player_id
                                   group by p.id
                                   order by last_earned_at desc
                                   limit :limit offset :offset),
            
                 last_played_trophy_suite as (select pts.player_id,
                                                     ts.id,
                                                     ts.name,
                                                     ts.platforms,
                                                     coalesce(ts.aws_image_url, ts.aws_image_url) as image,
                                                     last_played_time.value                       as last_played_at
                                              from app.psn_played_trophy_suite pts
                                                       join app.psn_trophy_suite ts on ts.id = pts.trophy_suite_id
                                                       join (select pts.player_id,
                                                                    max(pts.last_played_at) as value
                                                             from app.psn_played_trophy_suite pts
                                                             group by player_id) last_played_time
                                                            on last_played_time.value = pts.last_played_at),
            
                 last_earned_trophy as (select et.player_id,
                                               t.id,
                                               t.title,
                                               t.trophy_type,
                                               coalesce(t.aws_icon_url, t.psn_icon_url) as icon,
                                               ts.id                                    as trophy_suite_id,
                                               ts.name                                  as trophy_suite_name,
                                               last_earned_time.value                   as earned_at
                                        from app.psn_earned_trophy et
                                                 join app.psn_trophy t on t.id = et.trophy_id
                                                 join app.psn_trophy_suite ts on ts.id = t.trophy_suite_id
                                                 join (select et.player_id, max(et.earned_at) as value
                                                       from app.psn_earned_trophy et
                                                       group by player_id) last_earned_time
                                                      on last_earned_time.value = et.earned_at),
            
                 played_trophy_set_stats as (select pts.player_id,
                                                    count(*) as total_played_trophy_sets
                                             from app.psn_played_trophy_suite pts
                                             group by player_id),
            
                 earned_trophy_stats as (select et.player_id,
                                                count(*) filter ( where t.trophy_type = 'platinum' ) as total_platinum,
                                                count(*) filter ( where t.trophy_type = 'gold' )     as total_gold,
                                                count(*) filter ( where t.trophy_type = 'silver' )   as total_silver,
                                                count(*) filter ( where t.trophy_type = 'bronze' )   as total_bronze
                                         from app.psn_earned_trophy et
                                                  join app.psn_trophy t on t.id = et.trophy_id
                                         group by player_id)
            
            select player_search.id                                 as player_id,
                   player_search.pseudo                             as player_pseudo,
                   player_search.avatar                             as player_avatar,
            
                   last_played_trophy_suite.id                      as last_played_trophy_suite_id,
                   last_played_trophy_suite.name                    as last_played_trophy_suite_name,
                   last_played_trophy_suite.platforms               as last_played_trophy_suite_platform,
                   last_played_trophy_suite.image                   as last_played_trophy_suite_image,
                   last_played_trophy_suite.last_played_at          as last_played_trophy_suite_date,
            
                   last_earned_trophy.id                            as last_earned_trophy_id,
                   last_earned_trophy.title                         as last_earned_trophy_title,
                   last_earned_trophy.trophy_type                   as last_earned_trophy_type,
                   last_earned_trophy.icon                          as last_earned_trophy_icon,
                   last_earned_trophy.trophy_suite_id               as last_earned_trophy_trophy_suite_id,
                   last_earned_trophy.trophy_suite_name             as last_earned_trophy_trophy_suite_name,
                   last_earned_trophy.earned_at                     as last_earned_trophy_date,
            
                   played_trophy_set_stats.total_played_trophy_sets as total_played_trophy_sets,
            
                   earned_trophy_stats.total_platinum               as total_earned_platinum,
                   earned_trophy_stats.total_gold                   as total_earned_gold,
                   earned_trophy_stats.total_silver                 as total_earned_silver,
                   earned_trophy_stats.total_bronze                 as total_earned_bronze
            
            from player_search
                     join last_played_trophy_suite on player_search.id = last_played_trophy_suite.player_id
                     join last_earned_trophy on player_search.id = last_earned_trophy.player_id
                     join played_trophy_set_stats on player_search.id = played_trophy_set_stats.player_id
                     join earned_trophy_stats on player_search.id = earned_trophy_stats.player_id
            order by player_search.last_earned_at desc
            """, nativeQuery = true)
    List<PlayerSearchItemDTO> searchPlayers(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = """
            with active as (select et.player_id, count(*) as trophy_count
                            from app.psn_earned_trophy et
                            where et.earned_at >= now() - interval '7 days'
                            group by et.player_id
                            order by trophy_count desc
                            limit :playerLimit),
                 ranked as (select et.*,
                                   row_number() over (partition by et.player_id order by et.earned_at desc) as rn
                            from app.psn_earned_trophy et
                                     join active a on a.player_id = et.player_id)
            select p.id                                         as playerId,
                   p.pseudo                                     as pseudo,
                   coalesce(p.aws_avatar_url, p.psn_avatar_url) as avatar,
                   a.trophy_count                               as recent_trophy_count,
                   t.id                                         as trophy_id,
                   t.title                                      as trophy_title,
                   t.trophy_type                                as trophy_type,
                   coalesce(t.aws_icon_url, t.psn_icon_url)     as trophy_icon,
                   t.description                                as trophy_description,
                   ts.id                                        as trophy_suite_id,
                   ts.name                                      as trophy_suite_name,
                   r.earned_at                                  as earned_at
            from ranked r
                     join active a on a.player_id = r.player_id
                     join app.psn_player p on p.id = r.player_id
                     join app.psn_trophy t on t.id = r.trophy_id
                     join app.psn_trophy_suite ts on ts.id = t.trophy_suite_id
            where r.rn <= :trophyLimit
            order by a.trophy_count desc, p.id, r.earned_at desc
            """, nativeQuery = true)
    List<RecentPlayerRow> searchRecentPlayerTrophies(
            int playerLimit,
            int trophyLimit,
            Instant limitDate
    );

    Optional<Player> findByPseudo(String pseudo);
}
