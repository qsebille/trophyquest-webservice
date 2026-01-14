package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.api.dto.trophy.EarnedTrophyDTO;
import fr.trophyquest.backend.domain.entity.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, UUID> {

    @Query(value = """
            select t.id,
                   t.rank,
                   t.title,
                   t.description,
                   t.trophy_type,
                   t.is_hidden,
                   coalesce(t.aws_icon_url, t.icon_url) as icon,
                   t.game_group_id,
                   null::timestamptz as earned_at
            from app.trophy t
            where t.trophy_set_id = :trophySetId
            order by rank
            """, nativeQuery = true)
    List<EarnedTrophyDTO> fetchTrophiesOfTrophySet(UUID trophySetId);

    @Query(value = """
            select t.id,
                   t.rank,
                   t.title,
                   t.description,
                   t.trophy_type,
                   t.is_hidden,
                   coalesce(t.aws_icon_url, t.icon_url) as icon,
                   t.game_group_id,
                   et.earned_at
            from app.trophy t
                left join app.earned_trophy et on et.trophy_id = t.id and et.player_id = :playerId
            where t.trophy_set_id = :trophySetId
            order by rank
            """, nativeQuery = true)
    List<EarnedTrophyDTO> fetchPlayerTrophiesForTrophySet(UUID trophySetId, UUID playerId);

}
