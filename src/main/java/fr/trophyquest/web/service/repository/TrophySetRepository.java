package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrophySetRepository extends JpaRepository<Game, Long> {

    @Query("""
                select distinct g.id
                from Game g
                where exists (
                    select 1
                    from IgdbCandidate c
                    where c.game = g and c.status = 'PENDING'
                )
                order by g.id
            """)
    Page<UUID> findGameIdsHavingPendingCandidate(Pageable pageable);

    @Query("""
                select distinct g
                from Game g
                join fetch g.igdbCandidates c
                join fetch c.candidate ig
                where g.id in :ids
                order by g.id
            """)
    List<Game> fetchGamesWithCandidatesByIds(@Param("ids") List<UUID> ids);
}
