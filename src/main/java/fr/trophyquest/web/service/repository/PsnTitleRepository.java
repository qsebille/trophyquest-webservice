package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.PsnTitle;
import fr.trophyquest.web.service.entity.projections.PlayedGameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PsnTitleRepository extends JpaRepository<PsnTitle, UUID> {

    @Query(value = """
            SELECT DISTINCT
                title.id,
                title.name,
                ts.platform,
                title.image_url,
                ut.last_played_at AS lastPlayedDate
            FROM psn_title title
                JOIN psn_user_title ut ON title.id = ut.title_id
                JOIN psn_title_trophy_set tts ON title.id = tts.title_id
                JOIN psn_trophy_set ts ON ts.id = tts.trophy_set_id
                JOIN psn_user u ON ut.user_id = u.id
            WHERE u.id = :userId
            ORDER BY last_played_at desc
                        OFFSET :pageNumber * :pageSize;
            """, nativeQuery = true)
    List<PlayedGameProjection> findByUserId(
            UUID userId,
            int pageNumber,
            int pageSize
    );

}
