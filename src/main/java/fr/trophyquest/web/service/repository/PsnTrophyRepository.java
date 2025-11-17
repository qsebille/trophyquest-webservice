package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.PsnTrophy;
import fr.trophyquest.web.service.entity.projections.UserTrophyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PsnTrophyRepository extends JpaRepository<PsnTrophy, UUID> {

    @Query(value = """
            SELECT tr.*
            FROM psn_title ti
                JOIN psn_title_trophy_set tts ON tts.title_id = ti.id
                JOIN psn_trophy_set ts ON ts.id = tts.trophy_set_id
                JOIN psn_trophy tr ON tr.trophy_set_id = ts.id
            WHERE ti.id = :titleId
            ORDER BY rank
            """, nativeQuery = true)
    List<PsnTrophy> getGameTrophies(UUID titleId);

    @Query(value = """
            SELECT
                t.id,
                t.rank,
                t.name,
                t.description,
                t.trophy_type,
                t.is_hidden,
                t.icon_url,
                title.id AS gameId,
                title.name AS gameName,
                t.game_group_id AS gameGroup,
                et.earned_timestamp AS earnedAt
            FROM psn_title title
                JOIN psn_title_trophy_set tts ON tts.title_id = title.id
                JOIN psn_trophy_set ts ON ts.id = tts.trophy_set_id
                JOIN psn_trophy t ON t.trophy_set_id = ts.id
                LEFT JOIN psn_earned_trophy et ON et.trophy_id = t.id AND et.user_id = '0913d787-830f-5bf0-abfb-f7f620e74cae'
            WHERE title.id = :titleId
            ORDER BY rank;
            """, nativeQuery = true)
    List<UserTrophyProjection> getUserGameTrophies(
            UUID userId,
            UUID titleId
    );

    @Query(value = """
            SELECT
                t.id,
                t.rank,
                t.name,
                t.description,
                t.trophy_type,
                t.is_hidden,
                t.icon_url,
                title.id AS gameId,
                title.name AS gameName,
                t.game_group_id AS gameGroup,
                et.earned_timestamp AS earnedAt
            FROM psn_title title
                     JOIN psn_title_trophy_set tts ON tts.title_id = title.id
                     JOIN psn_trophy_set ts ON ts.id = tts.trophy_set_id
                     JOIN psn_trophy t ON t.trophy_set_id = ts.id
                     LEFT JOIN psn_earned_trophy et ON et.trophy_id = t.id
                     LEFT JOIN psn_user u ON u.id = et.user_id
            WHERE u.id = :userId
            ORDER BY earnedAt desc
            LIMIT :pageSize OFFSET :pageNumber * :pageSize;
            """, nativeQuery = true)
    List<UserTrophyProjection> getUserTrophies(
            UUID userId,
            int pageNumber,
            int pageSize
    );

}
